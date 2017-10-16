package interpreter;

import java.util.List;
import java.util.ArrayList;

public class FunctionCallNode extends Node
{
    public Node name;
    public List<Parameter> actualParameters;
    public Parser parser;
            
    public FunctionCallNode() {}
    
    public FunctionCallNode(Node name,
                            List<Parameter> actualParameters,
                            Parser parser)
    {
        this.name = name;
        this.actualParameters = actualParameters;
        this.parser = parser;
    }    
    
    public Object eval()
    {   

        Function function = (Function) name.eval();
        
        List<BoundParameter> boundParameters = new ArrayList();
        if (function.getParameters() != null)
        {
            if (actualParameters != null)            
            {
                if (actualParameters.size() < function.getParameters().size())
                {
                    System.out.println("Too Few Parameters in Function Call: "
                                        + function.getName());
                    System.exit(0);
                }
                else if (actualParameters.size() > function.getParameters().size())
                {
                    System.out.println("Too Many Parameters in Function Call: "
                                        + function.getName());
                    System.exit(0);
                }
                else
                {   
                    for (int index = 0; index < actualParameters.size(); index++)
                    {
                        String name = function.getParameters().get(index).getName();
                        Object value = actualParameters.get(index).getValue();
                                                                      
                        boundParameters.add(new BoundParameter(name, value));
                    }
                }
            }
        }                
        return parser.ExecuteFunction(function, boundParameters);
    }
}