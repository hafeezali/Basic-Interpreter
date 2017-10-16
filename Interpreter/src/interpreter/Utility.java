package interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Utility
{
// ~~~~ INLINE FUNCTIONS START ~~~~
    public static List<Node> CreateInlineFunctions(Parser parser)
    {        
        List<Node> inlineFunctions = new LinkedList<Node>();
                
        inlineFunctions.add(CreatePrintFunction(parser));
        inlineFunctions.add(CreatePrintlnFunction(parser));
        inlineFunctions.add(CreateReadlnFunction(parser));
        
        return inlineFunctions;
    }
    
    public static Node CreatePrintFunction(Parser parser)
    {        
        String functionName = "print";
        List<Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("writee"));
        
        List<Node> statements = new LinkedList<Node>();
        statements.add(new PrintCommand(parser));
        Node functionBody = new BlockNode(statements);
                
        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        
        return functionVariable;
    }   

    public static Node CreatePrintlnFunction(Parser parser)
    {   
        String functionName = "println";
        List<Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("writee"));
        
        List<Node> statements = new LinkedList<Node>();
        statements.add(new PrintlnCommand(parser));
        Node functionBody = new BlockNode(statements);
                
        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        
        return functionVariable;
    }
    
    public static Node CreateReadlnFunction(Parser parser)
    {
        String functionName = "readln";
        List<Parameter> parameters = new ArrayList();

        List<Node> statements = new LinkedList<Node>();
        statements.add(new ReadlnCommand(parser));
        Node functionBody = new BlockNode(statements);

        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);

        return functionVariable;
    }                    
// ~~~~ INLINE FUNCTIONS END ~~~~

// ~~~~ UTILITY METHODS START ~~~~
    public static void Write(Object obj)
    {
        System.out.print(obj);
    }
    
    public static void Writeln(Object obj)
    {
        System.out.println(obj);
    }

    public static void Writeln()
    {
        System.out.println();
    }
        
    public static void PrettyPrint(List<Token> tokens)
    {
        int numberCount = 0;
        int opCount = 0;
        for (Token token: tokens)
        {
            if (token.type == TokenType.NUMBER)
            {
                Writeln("Number....: " + token.text);
                numberCount++;
            }
            else
            {
                Writeln("Operator..: " + token.type);
                opCount++;
            }                
        }
        Writeln("You have got "+
                numberCount +
                " different number and " + 
                opCount
                +" operators.");
    }
// ~~~~ UTILITY METHODS END ~~~~       
}