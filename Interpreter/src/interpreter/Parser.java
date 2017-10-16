package interpreter;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

class Parser
{            
    public int currentTokenPosition = 0;
    public List<Token> tokens;
    
    public Map symbolTable = new HashMap();
    
    public Parser() {}    
    public Parser(List<Token> tokens)
    {
        this.tokens = tokens;
    }                
    public List<Token> getTokens()
    {
        return tokens;
    }    
    
// ~~~~Language Constructs Parsing Methods Start~~~~
    public RootNode Program()
    {
        MatchAndEat(TokenType.SCRIPT);
        return new RootNode(Block(), Utility.CreateInlineFunctions(this));
    }

    public BlockNode Block()
    {   
        List<Node> statements = new LinkedList<Node>();        
        while ( CurrentToken().type != TokenType.END)
        {                       
            statements.add(Statement());
        }                        
        MatchAndEat(TokenType.END);               
        return new BlockNode(statements);
    }

    public Node Assignment()
    {
        Node node = null;
        String name = MatchAndEat(TokenType.KEYWORD).text;
        MatchAndEat(TokenType.ASSIGNMENT);

        Node value = Expression();
        node = new AssignmentNode(name, value, this);
        
        return node;
    }
    
    public Node While()
    {
        Node condition, body;        
        MatchAndEat(TokenType.WHILE);
        condition = Expression();
        body = Block();        
        return new WhileNode(condition, body);
    }
    
    public Node If()
    {
        Node condition=null, thenPart=null, elsePart=null;
        
        MatchAndEat(TokenType.IF);
        condition = Expression();
        thenPart = Block();
        if ( CurrentToken().type == TokenType.ELSE )
        {
            MatchAndEat(TokenType.ELSE);
            if ( CurrentToken().type == TokenType.IF ) elsePart = If();
            else elsePart = Block();
        }        
        return new IfNode(condition, thenPart, elsePart);
    }    
       
    public Node Statement()
    {
        Node node = null;        
        TokenType type = CurrentToken().type;

        if (IsAssignment())
        {
            node = Assignment();
        }      
        else if (IsWhile())
        {                           
            node = While();
        }
        else if (IsIfElse())
        {               
            node = If();
        }
        else if (IsFunctionCall())
        {                    
            node = FunctionCall();
        }        
        else
        {        
            Utility.Writeln("Unknown language construct: " 
                         + CurrentToken().text + CurrentToken().type);
            System.exit(0);
        }
        return node;       
    }        
// ~~~~Language Constructs Parsing Methods End~~~~

// ~~~~Function Parsing and Calling Methods Start~~~~    
    public Node FunctionCall()
    {                
        String functionName = MatchAndEat(TokenType.KEYWORD).text;        

        Node calleeFunctionName = new VariableNode(functionName, this);
        MatchAndEat(TokenType.LEFT_PAREN);
        List<Parameter> actualParameters = FunctionCallParameters();
        MatchAndEat(TokenType.RIGHT_PAREN);
                        
        Node functionCallNode;
        functionCallNode = new FunctionCallNode(calleeFunctionName,        
                actualParameters, this);
        
        return functionCallNode;
    }

    public List FunctionCallParameters()
    {
        List<Parameter> actualParameters = null;
        Node expression = Expression();
        if (expression != null)
        {
            actualParameters = new ArrayList();
            actualParameters.add(new Parameter(expression));
            while (CurrentToken().type == TokenType.COMMA)
            {
                MatchAndEat(TokenType.COMMA);
                actualParameters.add(new Parameter(Expression()));
            }            
        }
            
        return actualParameters;
    }
        
    public Object ExecuteFunction(Function function, List boundParameters)
    {
        Map<String, Object> savedSymbolTable =
                new HashMap<String, Object>(symbolTable);
         
        for (int index = 0; index < boundParameters.size(); index++)
        {
            BoundParameter param = (BoundParameter) boundParameters.get(index);
            setVariable(param.getName(), param.getValue());
        }
        
        Object ret = function.eval();

        symbolTable = savedSymbolTable;

        return ret;
    }  
// ~~~~Function Parsing and Calling Methods End~~~~


//~~~~Expression Parsing Methods Start~~~~
    public Node Multiply()
    {        
        MatchAndEat(TokenType.MULTIPLY);
        return Factor();
    }

    public Node Divide()
    {
        MatchAndEat(TokenType.DIVIDE);
        return Factor();
    }
    
    public Node Add()
    {                
        MatchAndEat(TokenType.ADD);
        return Term();
    }

    public Node Subtract()
    {
        MatchAndEat(TokenType.SUBTRACT);
        return Term();
    }

    public Node Factor()
    {
        Node result = null;     
        
        if (CurrentToken().type == TokenType.LEFT_PAREN)
        {
            MatchAndEat(TokenType.LEFT_PAREN);
            result = Expression();
            MatchAndEat(TokenType.RIGHT_PAREN);
        }
        else if (IsNumber())
        {
            Token token = MatchAndEat(TokenType.NUMBER);
            result = new NumberNode(new Integer(token.text).intValue());
        }
        else if (IsString())
        {               
            Token token = MatchAndEat(TokenType.STRING);
            result = new StringNode(new String(token.text));
        }
        else if (IsKeyWord())
        {
            result = Variable();
        }
                
        return result;
    }

    public Node Variable()
    {
        Node node = null;
        
        if (NextToken().type == TokenType.LEFT_PAREN)
        {
            node = FunctionCall();
        }
        else
        {
            Token token = MatchAndEat(TokenType.KEYWORD);
            Node varNode = new VariableNode(token.text, this);
                                
            return varNode;
        }
        return node;
    }
    
        
    public Node SignedFactor()
    {
        if (CurrentToken().type == TokenType.SUBTRACT)
        {
            MatchAndEat(TokenType.SUBTRACT);
            Node node = new NegOpNode(Factor());
            return node;            
        }            
        return Factor();
    }
        
    public Node Term()
    {
        Node node = SignedFactor();
        while (IsMulOp(CurrentToken().type))
        {
            switch(CurrentToken().type)
            {
                case MULTIPLY:
                    node = new BinOpNode(TokenType.MULTIPLY, node, Multiply());
                break;
                case DIVIDE:
                    node = new BinOpNode(TokenType.DIVIDE, node, Divide());
                break;
            }
        }
        return node;        
    }

    public Node ArithmeticExpression()
    {        
        Node node = Term();                
        while (IsAddOp(CurrentToken().type))
        {            
            switch(CurrentToken().type)
            {
                case ADD:
                    node = new BinOpNode(TokenType.ADD, node, Add());
                break;
                case SUBTRACT:
                    node = new BinOpNode(TokenType.SUBTRACT, node, Subtract());
                break;                               
            }
        }
        return node;
    }

    public Node Less(Node node)
    {        
       MatchAndEat(TokenType.LESS);
       return new BinOpNode(TokenType.LESS, node, ArithmeticExpression());
    }

    public Node Greater(Node node)
    {
       MatchAndEat(TokenType.GREATER);       
       return new BinOpNode(TokenType.GREATER, node, ArithmeticExpression());
    }

    public Node Equal(Node node)
    {   
        MatchAndEat(TokenType.EQUAL);       
        return new BinOpNode(TokenType.EQUAL, node, ArithmeticExpression());          
    }

    public Node NotEqual(Node node)
    {        
        MatchAndEat(TokenType.NOTEQUAL);       
        return new BinOpNode(TokenType.NOTEQUAL, node, ArithmeticExpression());
    }
    
    public Node LessEqual(Node node)
    {        
       MatchAndEat(TokenType.LESSEQUAL);
       return new BinOpNode(TokenType.LESSEQUAL, node, ArithmeticExpression());
    }

    public Node GreaterEqual(Node node)
    {        
       MatchAndEat(TokenType.GREATEREQUAL);
       return new BinOpNode(TokenType.GREATEREQUAL, node, ArithmeticExpression());
    }
       
    public Node Relation()
    {        
        Node node = ArithmeticExpression();        
        if (IsRelOp(CurrentToken().type))
        {
            switch(CurrentToken().type)
            {                
                case LESS:
                    node = Less(node);
                break;               
                case GREATER:
                    node = Greater(node);
                break;
                case EQUAL:
                    node = Equal(node);                    
                break;                                                               
                case NOTEQUAL:
                    node = NotEqual(node);
                break;                
                case LESSEQUAL:
                    node = LessEqual(node);
                break;
                case GREATEREQUAL:
                    node = GreaterEqual(node);
                break;                
            }
        }
        return node;
    }
    
    public Node Expression()
    {
        return Relation();
    }
    
    public void PrettyPrint(List<Token> tokens)
    {
        int numberCount = 0;
        int opCount = 0;        
        for (Token token: tokens)
        {
            if (token.type == TokenType.NUMBER)
            {
                Utility.Writeln("Number....: " + token.text);
                numberCount++;
            }
            else
            {
                Utility.Writeln("Operator..: " + token.type);
                opCount++;
            }                
        }
        Utility.Writeln("You have got " + numberCount +
                           " different number and " 
                           + opCount +" operators.");
    }
//~~~~Expression Parsing Methods End~~~~
     
//~~~~Recognizer Methods Start~~~~
    public boolean IsMulOp(TokenType type)
    {     
        return type == TokenType.MULTIPLY || type == TokenType.DIVIDE;
    }

    public boolean IsAddOp(TokenType type)
    {        
        return type == TokenType.ADD || type == TokenType.SUBTRACT;
    }

    public boolean IsMultiDigitOp(TokenType type)
    {
        return type == TokenType.LESSEQUAL || type == TokenType.GREATEREQUAL;
    }
    
    public boolean IsRelOp(TokenType type)
    {   
        boolean lgOps = type == TokenType.LESS || 
        type == TokenType.GREATER;
        
        boolean eqOps = type == TokenType.EQUAL || 
        type == TokenType.NOTEQUAL;
        
        return eqOps || lgOps || IsMultiDigitOp(type);
    }
    
    public boolean IsNumber()
    {
        return CurrentToken().type == TokenType.NUMBER;
    }

    public boolean IsString()
    {
        return CurrentToken().type == TokenType.STRING;
    }

    public boolean IsKeyWord()
    {
        return CurrentToken().type  == TokenType.KEYWORD;
    }
    
    public boolean IsAssignment()
    {
        TokenType type = CurrentToken().type;
        return type == TokenType.KEYWORD && NextToken().type == TokenType.ASSIGNMENT;
    }
           
    public boolean IsWhile()
    {
        return CurrentToken().type == TokenType.WHILE;
    }

    public boolean IsIfElse()
    {
        TokenType type = CurrentToken().type;
        return type == TokenType.IF || type == TokenType.ELSE;
    }
    
    public boolean IsFunctionCall()
    {
        TokenType type = CurrentToken().type;
        return type == TokenType.KEYWORD && NextToken().type == TokenType.LEFT_PAREN;
    }    
//~~~~Recognizer Methods End~~~~
                        
//~~~~Token Manipulation Methods Start~~~~
    public Token GetToken(int offset)
    {
        if (currentTokenPosition + offset >= tokens.size())
        {
            return new Token("", TokenType.EOF);
        }
        return tokens.get(currentTokenPosition + offset);
    }        

    public Token CurrentToken()
    {
        return GetToken(0);
    }

    public Token NextToken()
    {
        return GetToken(1);
    }
    
    public void EatToken(int offset)
    {           
        currentTokenPosition = currentTokenPosition + offset;
    }
    
    public Token MatchAndEat(TokenType type)
    {       
        Token token = CurrentToken();
                
        if (CurrentToken().type != type)
            Utility.Writeln("Saw " + token.type + " but " + type + " expected");
        
        EatToken(1);
        return token;
    }
//~~~~Token Maniplation Methods End~~~~    

// ~~~~Symbol Table Methods Start~~~~
    public Object setVariable(String name, Object value)
    {        
        symbolTable.put(name, value);
        return value;
    }

    public Object getVariable(String name)
    {
        Object value = (Object) symbolTable.get(name);
        if (value != null) return value;
                
        return null;
    }
// ~~~~Symbol Table Methods End~~~~
}