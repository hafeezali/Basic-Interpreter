package interpreter;

public class BinOpNode extends Node
{
    public TokenType op;
    public Node left;
    public Node right;    
    
    public BinOpNode() {}

    public BinOpNode(TokenType op, Node left, Node right)
    {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public int ToInt(Node node)
    {
        Object res = node.eval();
        return ((Integer) res).intValue();
    }
    
    public boolean ToBoolean(Node node)
    {                
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }

    public Object ToObject(Node node)
    {
        return node.eval();
    }

    public Object eval()
    {                
        Object result = null;
        switch(op)
        {
            case ADD:
                try{
                    result = new Integer(ToInt(left) + ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(Add) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot add integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }
                }
            break;
            case SUBTRACT:
                try{
                    result = new Integer(ToInt(left) - ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(Subtract) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot subtract integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }                  
                }
            break;
            case MULTIPLY:
                try{
                    result = new Integer(ToInt(left) * ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(Multiply) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot multiply integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }

                }
            break;
            case DIVIDE:
                if (ToInt(right) == 0)
                {
                    System.out.println("Error: Division by Zero!");
                    System.exit(0);
                }
                try{
                    result = new Integer(ToInt(left) / ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(Divide) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot divide integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }
                }
            break;
            case LESS:
                try{
                    result = new Boolean(ToInt(left) < ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(lesser than) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot compare(lesser than) integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }                   
                }
            break;
            case GREATER:
                try{
                    result = new Boolean(ToInt(left) > ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(greater than) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot compare(greater than) integer and string. Tokens are " + tokenL + " and " + tokenR);
                    } 
                }
            break;
            case EQUAL:
                result = new Boolean(ToObject(left).equals(ToObject(right)));
            break;
            case NOTEQUAL:
                result = new Boolean(!ToObject(left).equals(ToObject(right)));
            break;
            case LESSEQUAL:
                try{
                    result = new Boolean(ToInt(left) <= ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(lesser than equal to) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot compare(lesser than equal to) integer and string. Tokens are " + tokenL + " and " + tokenR);
                    } 
                }
            break;
            case GREATEREQUAL:
                try{
                    result = new Boolean(ToInt(left) >= ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else{
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else{
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        System.out.println("Error: Invalid operation(greater than equal to) for type string. Tokens are " + tokenL + " and " + tokenR);
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        String tokenL = null, tokenR = null;
                        if(left instanceof VariableNode){
                            VariableNode l = (VariableNode) left;
                            tokenL = l.varName;
                        }
                        else if(left instanceof StringNode){
                            StringNode l = (StringNode) left;
                            tokenL = l.text;
                        }
                        else{
                            NumberNode l = (NumberNode) left;
                            tokenL = left.eval().toString();
                        }
                        if(right instanceof VariableNode){
                            VariableNode r = (VariableNode) right;
                            tokenR = r.varName;
                        }
                        else if(right instanceof StringNode){
                            StringNode r = (StringNode) right;
                            tokenR = r.text;
                        }
                        else{
                            NumberNode r = (NumberNode) right;
                            tokenR = r.eval().toString();
                        }
                        System.out.println("Error: Cannot compare(greater than equal to) integer and string. Tokens are " + tokenL + " and " + tokenR);
                    }
                }
            break;                                                                                     
        }
        return result;
    }   
}