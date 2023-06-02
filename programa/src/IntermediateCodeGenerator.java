package src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntermediateCodeGenerator {
    private StringBuilder code;
    private int tempCount = 0;
    Map<String, String> dataTypes = new HashMap<String, String>();
    private String funcionActual = "";
    private int forCount = 0;
    private int doWhileCount = 0;
    private int whileCount = 0;
    private int ifCount = 0;
    private int elifCount = 0;
    private int elseCount = 0;
    private boolean asignando = false;
    private ArrayList<String> ciclosStack = new ArrayList<String>();
    private String cicloActual = "";

    public IntermediateCodeGenerator() {
        code = new StringBuilder();
        dataTypes.put("int", "dataInt");
        dataTypes.put("float", "dataFloat");
        dataTypes.put("char", "dataChar");
        dataTypes.put("String", "dataString");
        dataTypes.put("bool", "dataBool");



    }

    public String generateCode(ASTNode root){
        ArrayList<ASTNode> children = root.getChildren();
        //System.out.println(children.toString());
        for (ASTNode child : children) {
            System.out.println("=====#"+child.getType());
            String childType = child.getType();
            if(childType.equals("programa")){
                generateCode(child);
            }
            else if(childType.equals("init")){
                generateCode(child);
            }
            else if(childType.equals("declaraFuncion")){
                code.append("\n"+child.getValue()+":\n");
                funcionActual = child.getValue().toString();
                generateCode(child);
                this.forCount = 0;

            }
            else if(childType.equals("bloque")){
                System.out.println("bloque");
                generateCode(child);
            }
            else if(childType.equals("expresion")){
                System.out.println("expresion");
                generateCode(child);
            }
            else if(childType.equals("llamaFuncion")){
                System.out.println("llamaFuncion");
                int cantParams = 0;
                if(child.getChildren().size()>1){
                    ArrayList<ASTNode> params = child.getChildren().get(1).getChildren();
                    int i = tempCount;
                    for (ASTNode param : params) {
                        code.append("  t"+tempCount+" = "+param.getValue()+"\n");
                        tempCount++;
                        cantParams++;
                    }
                    for(int j = i; j<tempCount;j++){
                        code.append("  param t"+j+"\n");
                    }
                }
                if(asignando){
                    code.append("  t"+tempCount+" = call "+child.getChildren().get(0).getValue()+", "+ cantParams+"\n");
                }
                else{
                    code.append("  call "+child.getChildren().get(0).getValue()+", "+ cantParams+"\n");
                }
                tempCount++;         
            }else if(childType.equals("declaraVar")){
                System.out.println("declaraVar");
                asignando = true;
                code.append("\n  "+dataTypes.get(child.getChildren().get(0).getValue())+" "+child.getValue()+"\n");
                if(child.getChildren().size()>1){
                    generateCode(child.getChildren().get(1));
                    code.append("  "+child.getValue()+" = t"+(tempCount-1)+"\n");
                }
                asignando = false;
            }
            else if(childType.equals("asignacion")){
                System.out.println("asignacion");
                asignando = true;
                generateCode(child);
                code.append("  "+child.getValue()+" = t"+(tempCount-1)+"\n");
                asignando = false;
            }
            else if(childType.equals("literal_int")){
                System.out.println("literal_int");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_float")){
                System.out.println("literal_float");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_char")){
                System.out.println("literal_char");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_string")){
                System.out.println("literal_string");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_bool")){
                System.out.println("literal_bool");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("declaraArray")){
                asignando = true;
                code.append("\n  dataArray "+child.getValue()+"["+child.getChildren().get(1).getValue().toString()+"]\n");
                if(child.getChildren().size()>2){
                    generateCode(child.getChildren().get(2));
                    int numElemento = child.getChildren().get(2).getChildren().size();
                    int pos = 0;
                    for (ASTNode elemento : child.getChildren().get(2).getChildren()) {
                        code.append("  "+child.getValue()+"["+pos+"] = t"+(tempCount-(numElemento))+"\n");
                        numElemento--;
                        pos++;
                    }      
                } 
                asignando = false;               
            }
            else if(childType.equals("setValorArray")){
                asignando = true;
                code.append("  t"+tempCount+" = "+((ASTNode)child.getChildren().get(1).getValue()).getChildren().get(0).getChildren().get(0).getType()+"\n");
                code.append("  "+child.getValue()+"["+child.getChildren().get(0).getValue()+"] = t"+tempCount+"\n");
                tempCount++;

            }

            else if(childType.equals("IDENTIFIER")){
                System.out.println("IDENTIFIER");
                if(child.getChildren().get(0).getValue() == null){
                    code.append("  t"+tempCount+" = "+child.getChildren().get(0).getType()+"\n");
                }else{
                    code.append("  t"+tempCount+" = "+child.getChildren().get(0).getValue()+"\n");
                
                }
                tempCount++;
            }
            else if(childType.equals("expresionBinaria")){
                System.out.println("expresionBinaria");
                ASTNode temp =  new ASTNode("");

                temp.addChild((ASTNode)child.getChildren().get(2).getValue());
                generateCode(temp);
                int i = tempCount-1;
                temp =  new ASTNode("");

                temp.addChild((ASTNode)child.getChildren().get(3).getValue());
                generateCode(temp);
                int j = tempCount;
                code.append("  t"+tempCount+" = t"+(i)+" "+child.getChildren().get(1).getValue()+" t"+(tempCount-1)+"\n");
                tempCount++;
            }
            else if(childType.equals("operadorUnario")){
                ArrayList<ASTNode> hijosOpUnario = child.getChildren();
                if(hijosOpUnario.get(0).getValue().equals("IDENTIFIER")){
                    code.append("  t"+tempCount+" = "+hijosOpUnario.get(1).getValue()+hijosOpUnario.get(2).getValue()+"\n");
                }
                else{
                    code.append("  t"+tempCount+" = "+hijosOpUnario.get(1).getValue()+((ASTNode)hijosOpUnario.get(2).getValue()).getChildren().get(0)+"\n");
                }
                tempCount++;
            }
            else if(childType.equals("estructuraControl")){
                ArrayList<ASTNode> hijosEstructuraControl = child.getChildren();
                if(hijosEstructuraControl.get(0).getValue().equals("forStm")){
                    forCount++;
                    int forActual = forCount;
                    ciclosStack.add("_"+funcionActual+"_for"+forActual+"_end");
                    cicloActual = "_"+funcionActual+"_for"+forActual+"_end";
                    System.out.println("_"+funcionActual+"_for"+forActual+"_init:");
                    code.append("\n_"+funcionActual+"_for"+forActual+"_init:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);

                    System.out.println("_"+funcionActual+"_for"+forActual+"_eval:");
                    code.append("\n_"+funcionActual+"_for"+forActual+"_eval:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode(temp);

                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_for"+forActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_for"+forActual+"_end\n");
                    System.out.println("_"+funcionActual+"_for"+forActual+"_update:");
                    code.append("\n_"+funcionActual+"_for"+forActual+"_update:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                    generateCode(temp);
                    code.append("  goto _"+funcionActual+"_for"+forActual+"_eval\n");


                    System.out.println("_"+funcionActual+"_for"+forActual+"_body:");
                    code.append("\n_"+funcionActual+"_for"+forActual+"_body:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(4).getValue());
                    generateCode(temp);
                    code.append("  goto _"+funcionActual+"_for"+forActual+"_update\n");

                    System.out.println("_"+funcionActual+"_for"+forActual+"_end:");
                    code.append("\n_"+funcionActual+"_for"+forActual+"_end:\n");
                    
                    if(ciclosStack.size() > 0){
                        ciclosStack.remove(ciclosStack.size()-1);
                        if(ciclosStack.size() > 0)
                            cicloActual = ciclosStack.get(ciclosStack.size()-1);
                    }


                }
            
                else if(hijosEstructuraControl.get(0).getValue().equals("doWhileStm")){
                    doWhileCount++;
                    int doWhileActual = doWhileCount;
                    ciclosStack.add("_"+funcionActual+"_doWhile"+doWhileActual+"_end");
                    cicloActual = "_"+funcionActual+"_doWhile"+doWhileActual+"_end";
                    System.out.println("_"+funcionActual+"_doWhile"+doWhileActual+"_body:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileActual+"_body:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);

                    System.out.println("_"+funcionActual+"_doWhile"+doWhileActual+"_eval:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileActual+"_eval:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_doWhile"+doWhileActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_doWhile"+doWhileActual+"_end\n");

                    System.out.println("_"+funcionActual+"_doWhile"+doWhileActual+"_end:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileActual+"_end:\n");
                
                    if(ciclosStack.size() > 0){
                        ciclosStack.remove(ciclosStack.size()-1);
                        if(ciclosStack.size() > 0)
                            cicloActual = ciclosStack.get(ciclosStack.size()-1);
                    }
                }
                else if(hijosEstructuraControl.get(0).getValue().equals("whileStm")){
                    whileCount++;
                    int whileActual = whileCount;
                    ciclosStack.add("_"+funcionActual+"_while"+whileActual+"_end");
                    cicloActual = "_"+funcionActual+"_while"+whileActual+"_end";

                    System.out.println("_"+funcionActual+"_while"+whileActual+"_eval:");
                    code.append("\n_"+funcionActual+"_while"+whileActual+"_eval:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_while"+whileActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_while"+whileActual+"_end\n");

                    System.out.println("_"+funcionActual+"_while"+whileActual+"_body:");
                    code.append("\n_"+funcionActual+"_while"+whileActual+"_body:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode(temp);
                    code.append("  goto _"+funcionActual+"_while"+whileActual+"_eval\n");

                    System.out.println("_"+funcionActual+"_while"+whileActual+"_end:");
                    code.append("\n_"+funcionActual+"_while"+whileActual+"_end:\n");
                
                    if(ciclosStack.size() > 0){
                        ciclosStack.remove(ciclosStack.size()-1);
                        if(ciclosStack.size() > 0)
                            cicloActual = ciclosStack.get(ciclosStack.size()-1);
                    }
                }

                else if(hijosEstructuraControl.get(0).getValue().equals("ifStm")){
                    ifCount++;
                    int ifActual = ifCount;
                    System.out.println("_"+funcionActual+"_if"+ifActual+"_eval:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_eval:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_if"+ifActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_else\n");

                    System.out.println("_"+funcionActual+"_if"+ifActual+"_body:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_body:\n");
                    generateCode((ASTNode)hijosEstructuraControl.get(2).getValue());
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_end\n");

                    if(hijosEstructuraControl.size() >= 4){
                        System.out.println("_"+funcionActual+"_if"+ifActual+"_else:");
                        code.append("\n_"+funcionActual+"_if"+ifActual+"_"+((ASTNode)hijosEstructuraControl.get(3).getValue()).getChildren().get(0).getValue().toString()+":\n");
                        temp =  new ASTNode("");
                        temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                        generateCode(temp);

                        if(hijosEstructuraControl.size() == 5){
                            System.out.println("_"+funcionActual+"_if"+ifActual+"_else:");
                            code.append("\n_"+funcionActual+"_if"+ifActual+"_else:\n");
                            temp =  new ASTNode("");
                            temp.addChild((ASTNode)hijosEstructuraControl.get(4).getValue());
                            generateCode(temp);
                        }

                    }
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_end\n");
                    System.out.println("_"+funcionActual+"_if"+ifActual+"_end:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_end:\n");
                    
                }
                else if(hijosEstructuraControl.get(0).getValue().equals("elifStm")){
                    elifCount++;
                    int elifActual = elifCount;
                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_eval:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_eval:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_elif"+elifActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_else\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_body:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_body:\n");
                    generateCode((ASTNode)hijosEstructuraControl.get(2).getValue());
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_else:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_else:\n");
                    if(hijosEstructuraControl.size() == 4){
                        temp =  new ASTNode("");
                        temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                        generateCode(temp);
                    }    
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_end:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_end:\n");
                    
                }
                else if(hijosEstructuraControl.get(0).getValue().equals("elseStm")){
                    elseCount++;
                    int elseActual = elseCount;

                    System.out.println("_"+funcionActual+"_else"+elseActual+"_body:");
                    code.append("\n_"+funcionActual+"_else"+elseActual+"_body:\n");
                    generateCode((ASTNode)hijosEstructuraControl.get(1).getValue());
                    code.append("  goto _"+funcionActual+"_else"+elseActual+"_end\n");

                    System.out.println("_"+funcionActual+"_else"+elseActual+"_end:");
                    code.append("\n_"+funcionActual+"_else"+elseActual+"_end:\n");
                }
            }
            
            else if(childType.equals("returnStm")){
                generateCode(child);
                code.append("  return t"+(tempCount-1)+"\n");
            }
            else if(childType.equals("value")){
                generateCode(child);
            }
            else if(childType.equals("breakStm")){
                code.append("  goto "+cicloActual+"\n");
            }
                
            
            
            
        }
        return code.toString();
    }


    public String auxGenerator(ASTNode noode){
        IntermediateCodeGenerator aux = new IntermediateCodeGenerator();


        return aux.generateCode(noode);
    }
}
