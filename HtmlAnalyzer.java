import java.io.*;
import java.net.*;
import java.util.Stack;


public class HtmlAnalyzer {
    public static void main(String[] args) throws Exception{
        String url = args[0];
        
        URL link = new URL(url);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(link.openStream())
        );
        
        String linha;
        int deeperLevel = 0;
        String deeperText = "";
        Stack<String> tags = new Stack<String>();
        boolean isValid = true;

        while((linha = in.readLine()) != null){
            linha = linha.trim();
           
            if(isTag(linha)){
                if(linha.charAt(0) != '<' || linha.charAt(linha.length() - 1) != '>'){
                    isValid = false;
                    break;
                }
                
                if(linha.charAt(1) == '/'){
                    if(tags.empty()){
                        isValid = false;
                        break;
                    }

                    if(!isEndTagValid(linha, tags)){
                        isValid = false;
                        break;
                    }
                } else
                    tags.push(linha.substring(1, linha.length() - 1));
            } else{
                if(tags.size() > deeperLevel){
                    deeperLevel = tags.size();
                    deeperText = linha;
                }
            }
        }

        if(isValid){
            System.out.println(deeperText);
        } else{
            System.out.println("malformed HTML");
        }
    }

    private static boolean isTag(String linha){
        if(!linha.contains("<") && !linha.contains(">"))
            return false;

        return true;
    }

    private static boolean isEndTagValid(String linha ,  Stack<String> tags  ){ 
        if(!(tags.pop().equals(linha.substring(2, linha.length() - 1))))
            return false;

        return true;
    }
}
