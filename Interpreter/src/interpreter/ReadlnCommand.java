package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadlnCommand extends Node
{
    public Parser parser;

    public ReadlnCommand()
    {
    }

    public ReadlnCommand(Parser parser)
    {
        this.parser = parser;
    }

    public Object eval()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String result = "";
        try
        {
            result = br.readLine();
        } catch (IOException e)
        {
            System.out.println("Error in Readln.eval() method" + e);
        }
        return result;
    }
}
