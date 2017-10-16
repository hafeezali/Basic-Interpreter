package interpreter;

public class Parameter
{
    private String name;
    private Node value;

    public Parameter(Node value)
    {
        this.value = value;
    }
    
    public Parameter(String name)
    {
        this.name = name;
    }

    public Object eval()
    {
        return value.eval();
    }

    public String getName()
    {
        return name;
    }

    public Object getValue()
    {
        return value.eval();
    }
}