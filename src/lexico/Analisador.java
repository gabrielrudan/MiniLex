package lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.crypto.AEADBadTagException;
/**
 * 
 * Este analisador faz uso de elementos do projeto de arthurmteodoro (ano 2017) 
 * e notas de aula do Prof. Andrei Formiga (ano 2010)
 *
 */
public class Analisador {

            private BufferedReader arquivo;
	    private String arquivoNome;
	    private String linha;
	    private int numeroLinha;
	    private int posicaoLinha;
            private String lex[];
	    
    public void abreArquivo(String nomeArquivo) throws IOException {
    	arquivo = new BufferedReader(new FileReader(nomeArquivo));
    	arquivoNome = nomeArquivo;
    	linha = arquivo.readLine().concat("\n");
    	numeroLinha=1;
    	posicaoLinha=0;
    }
    public void fechaArquivo(String nomeArquivo) throws IOException {
    	arquivo.close();
    	linha="";
    	this.numeroLinha = 0;
        this.posicaoLinha = 0;
    }
    public char getChar() {
    	if(linha==null) return 0;//caso a primeira linha lida ao abrir o arquivo seja nula
    	if(posicaoLinha == linha.length()) {//se j� leu todos os caracteres
    		try {
				linha = arquivo.readLine();//busca-se uma nova linha
			} catch (IOException e) {
				e.printStackTrace();
			}
    		if(linha == null)//final do arquivo
    		{
                numeroLinha++;
                return 0;
            }
    		else {//tem linha nova
    			linha = linha.concat("\n");
                numeroLinha++;
                posicaoLinha = 0;
    		}
    	}
    	char ch = linha.charAt(posicaoLinha);//ler o primeiro caracter
        posicaoLinha++;//incrementa a posi��o
        return ch;//retorna esse caracter
    }
    public boolean proximoChar(char c) {//compara um caracter recebido com o pr�ximo a ser lido
    	char proximo = getChar();
    	return c==proximo;
    }
    
    private boolean isChar(char c){
        return (c>='a' && c<='z') || (c>='A' && c<='Z');
    }
    
    private boolean isDigito(char c){
        return c>='0' && c<='9';
    }
    
    private boolean isPonto(char c){
        return c == '.';
    }
    
    private boolean isSpecial(char c){
        return (c>='!' && c<='/')||(c>=':' && c<='~')||(c>='€' && c<='ý');
    }
    
    private String verificaVar(char c){
        String lexAux = "";
        lexAux += c;
        posicaoLinha --;
        c = getChar();
        while (isChar(c) || isDigito(c) || c == '_') {                                                           
            
            c = getChar();
        
            if(Simbolos.verificaSimbolo(c)){
                              
            }else{
                lexAux += c;   
            }
        }
        return lexAux;
    }
    
    public Token capturaToken() throws IOException {
    	Token token = null;
    	Automato automato = Automato.PROGRAMA;
    	String lexema = "";
    	char c,d;
        
    	while(token == null)//o processo garante o encontro de um token, nem que este seja EOF
        {
            switch(automato)
            {   
                case PROGRAMA:
                    c = getChar();
                    switch(c){
                        case '<':
                            c = getChar();
                           switch(c){
                                case 'p':
                                    if(proximoChar('r')){
                                        if(proximoChar('o')){
                                            if(proximoChar('g')){                                        
                                                if(proximoChar(' ')){
                                                    c = getChar();
                                                    if(isChar(c)){
                                                        lexema += c;
                                                         c = getChar();
                                                        while (isChar(c) || isDigito(c) || c == '_') {                                                           
                                                           lexema += c;
                                                           c = getChar();
                                                        }
                                                    }
                                                    posicaoLinha --;
                                                    if(proximoChar('>')){
                                                       token = new Token(TipoToken.PROGRAMAINICIO, "<prog "+lexema+">", numeroLinha);
                                                       lexema = "";
                                                    }else{
                                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                       posicaoLinha --;
                                                    }
                                                } else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                    posicaoLinha--;
                                                }
                                            }else{
                                               token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                               posicaoLinha --;
                                           }
                                       }else{
                                           token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                           posicaoLinha --;
                                       }
                                   }else{
                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                       posicaoLinha --;
                                   }
                                break;
                                case '/':
                                    c = getChar();
                                    switch(c){
                                        case 'p':
                                           if (proximoChar('r')) {
                                               if (proximoChar('o')) {
                                                   if (proximoChar('g')) {
                                                       if (proximoChar('>')) {
                                                           token = new Token(TipoToken.PROGRAMAFIM, "</prog>", numeroLinha);
                                                       } else {
                                                           token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                           posicaoLinha--;
                                                       }
                                                   } else {
                                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                       posicaoLinha--;
                                                   }
                                               } else {
                                                   token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                   posicaoLinha--;
                                               }
                                           } else {
                                               token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                               posicaoLinha--;
                                           }
                                        break;
                                        case 'e':
                                            if (proximoChar('n')) {
                                                if (proximoChar('t')) {
                                                    if (proximoChar('a')) {
                                                        if (proximoChar('o')) {
                                                            if (proximoChar('>')) {
                                                                token = new Token(TipoToken.DECLARAENTAOFIM, "</entao>", numeroLinha);
                                                            }else{
                                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                                posicaoLinha--;
                                                            }
                                                        }else{
                                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                            posicaoLinha--;
                                                        }
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                        posicaoLinha--;
                                                    }
                                                }else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                    posicaoLinha--;
                                                }
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                posicaoLinha--;
                                            }
                                        break;
                                        case 's':
                                            if (proximoChar('e')) {
                                                if (proximoChar('n')) {
                                                    if (proximoChar('a')) {
                                                        if (proximoChar('o')) {
                                                            if (proximoChar('>')) {
                                                                token = new Token(TipoToken.DECLARACAOSENAOFIM, "</senao>", numeroLinha);
                                                            }else{
                                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                                posicaoLinha--;
                                                            }
                                                        }else{
                                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                            posicaoLinha--;
                                                        }
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                        posicaoLinha--;
                                                    }
                                                }else{
                                                    posicaoLinha--;
                                                    if(proximoChar('>')){
                                                        token = new Token(TipoToken.DECLARACAOSEFIM, "</se>", numeroLinha);
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                        posicaoLinha--;
                                                    }
                                                }
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                posicaoLinha--;
                                            }
                                        break;
                                    }
                               break;
                               case 'e':
                                   if(proximoChar('n')){
                                       if(proximoChar('t')){
                                           if(proximoChar('a')){
                                               if(proximoChar('o')){
                                                   if(proximoChar('>')){
                                                       token = new Token(TipoToken.DECLARAENTAOINI, "<entao>", numeroLinha);
                                                   }else{
                                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                       posicaoLinha--;
                                                   }
                                               }else{
                                                   token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                   posicaoLinha--;
                                               }
                                           }else{
                                               token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                               posicaoLinha--;
                                           }
                                       }else{
                                           token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                           posicaoLinha--;
                                       }
                                   }else{
                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                       posicaoLinha--;
                                   }
                               break;
                               case 's':
                                   if (proximoChar('e')) {
                                       if (proximoChar('>')) {
                                            token = new Token(TipoToken.DECLARACAOSEINI, "<se>", numeroLinha);
                                       } else {
                                           posicaoLinha--;
                                           if (proximoChar('n')){
                                               if (proximoChar('a')){
                                                   if (proximoChar('o')){
                                                       if (proximoChar('>')){
                                                          token = new Token(TipoToken.DECLARACAOSENAOINI, "<senao>", numeroLinha); 
                                                       } else {
                                                          token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                          posicaoLinha-- ;
                                                       }
                                                   } else {
                                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                       posicaoLinha-- ;
                                                   }
                                               } else {
                                                   token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                   posicaoLinha-- ;
                                               }
                                           } else {
                                               token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                               posicaoLinha-- ;
                                           }
                                       }
                                   } else {
                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                       posicaoLinha--;
                                   }
                               break;
                               case '!':
                                   if (proximoChar('-')) {
                                       if (proximoChar('-')) {
                                           if (proximoChar(' ')) {
                                               int i = 0;
                                               c = getChar();
                                               while (c != '-') {
                                                   c = getChar();
                                                   i++;
                                                   if (i == 100000000) {
                                                       token = new Token(TipoToken.ERRO, "Comentário na fechado", numeroLinha);
                                                       break;
                                                   }
                                               }
                                               if (proximoChar('-')) {
                                                   if (proximoChar('>')) {
                                                   } else {
                                                       posicaoLinha--;
                                                       token = new Token(TipoToken.ERRO, "Comentário na fechado", numeroLinha);
                                                   }
                                               } else {
                                                   posicaoLinha--;
                                                   token = new Token(TipoToken.ERRO, "Comentário na fechado", numeroLinha);
                                               }
                                           } else {
                                               int i = 0;
                                               while (proximoChar('-')) {
                                                   System.out.println(i++);
                                               }
                                               if (proximoChar('-')) {
                                                   if (proximoChar('>')) {

                                                   } else {
                                                       posicaoLinha--;
                                                       token = new Token(TipoToken.ERRO, "Comentário na fechado", numeroLinha);
                                                   }
                                               } else {
                                                   posicaoLinha--;
                                               }
                                           }
                                       } else {
                                           posicaoLinha--;
                                       }
                                   } else {
                                       posicaoLinha--;
                                   }
                                   break;
                               default:
                                   if(proximoChar('=')){
                                       token = new Token(TipoToken.OPMENORIGUAL, "<=", numeroLinha);
                                   }else{
                                      posicaoLinha --;
                                      token = new Token(TipoToken.OPMENOR, "<", numeroLinha); 
                                   }
                                   
                               break;
                           }                        
                        break;
                        case '>':
                            if(proximoChar('=')){
                                    token = new Token(TipoToken.OPMAIORIGUAL, ">=", numeroLinha);
                                }else{
                                    posicaoLinha --;
                                    token = new Token(TipoToken. OPMAIOR, ">", numeroLinha);
                            }
                        break;
                        case '=':
                            if(proximoChar('=')){
                                token = new Token(TipoToken.OPIGUALIGUAL, "==", numeroLinha);
                            } else{
                                posicaoLinha--;
                                token = new Token(TipoToken.OPIGUAL, "=", numeroLinha);
                            }
                        break;
                        case '(':
                            token = new Token(TipoToken.ABREPAREN, "(", numeroLinha);
                        break;
                        case ')':
                            token = new Token(TipoToken.FECHAPAREN, ")", numeroLinha);
                        break;
                        case '!':
                            if(proximoChar('=')){
                                token = new Token(TipoToken.OPDIFERENTE, "!=", numeroLinha);
                            }
                        break;
                        case ';':
                            token = new Token(TipoToken.DECLARACAOVARFIM, ";", numeroLinha);
                        break;
                        case '&':
                            if(proximoChar('&')){
                                token = new Token(TipoToken.OPAND, "&&", numeroLinha);
                            }else {
                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                posicaoLinha --;
                            }
                        break;
                        case '|':
                            if(proximoChar('|')){
                                token = new Token(TipoToken.OPOR, "||", numeroLinha);
                            }else {
                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                posicaoLinha --;
                            }
                        break;
                        case '"':
                            c = getChar();
                            if(isChar(c) || isDigito(c)){
                                String carac = "";
                                carac += c ;
                                if(proximoChar('"')){
                                    token = new Token(TipoToken.VALCARAC, '"'+carac+'"', numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Tamanho do caractere excedido", numeroLinha);
                                }
                            }
                        break;
                        case '+':
                            token = new Token(TipoToken.OPARITMAIS, "+", numeroLinha);
                        break;
                        case '-':
                            token = new Token(TipoToken.OPARITMENOS, "-", numeroLinha);
                        break;
                        case '*':
                            token = new Token(TipoToken.OPARITMULTIPLICACAO, "*", numeroLinha);
                        break;
                        case '/':
                            token = new Token(TipoToken.OPARITDIVISAO, "/", numeroLinha);
                        break;
                        case ',':
                            token = new Token(TipoToken.OUTRAVAR, ",", numeroLinha);
                        break;
                        case 'v':
                            if(proximoChar('a')){
                                if(proximoChar('r')){
                                    c = getChar();
                                    if(Simbolos.verificaSimbolo(c)){
                                        token = new Token(TipoToken.DECLARACAOVARINI, "var", numeroLinha);
                                        posicaoLinha--;
                                    }else{
                                        posicaoLinha -=4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else {
                                posicaoLinha -= 2;
                                c = getChar();
                                if(isChar(c)){
                                    lexema = "";
                                    lexema += verificaVar(c);
                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                }
                            }
                        break;
                        case 'i':
                            if(proximoChar('n')){
                                if(proximoChar('t')){
                                    c = getChar();
                                    if(Simbolos.verificaSimbolo(c)){
                                        token = new Token(TipoToken.TIPOVAR, "int", numeroLinha);
                                        posicaoLinha--;
                                    }else{
                                        posicaoLinha -=4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -=3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else{
                                posicaoLinha -= 2;
                                c = getChar();
                                if(isChar(c)){
                                    lexema = "";
                                    lexema += verificaVar(c);
                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                }
                            }
                        break;
                        case 'f':
                            if(proximoChar('l')){
                                if(proximoChar('u')){
                                    if(proximoChar('t')){
                                        c = getChar();
                                        if(Simbolos.verificaSimbolo(c)){
                                            token = new Token(TipoToken.TIPOVAR, "flut", numeroLinha);
                                            posicaoLinha--;
                                        }else{
                                            posicaoLinha -=5;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else{
                                posicaoLinha --;
                                if(proximoChar('a')){
                                    if(proximoChar('l')){
                                        if(proximoChar('s')){
                                            if(proximoChar('e')){
                                                c = getChar();
                                                if(Simbolos.verificaSimbolo(c)){
                                                    token = new Token(TipoToken.VALFALSE, "false", numeroLinha);
                                                    posicaoLinha--;
                                                }else{
                                                    posicaoLinha -=6;
                                                    c = getChar();
                                                    if(isChar(c)){
                                                        lexema = "";
                                                        lexema += verificaVar(c);
                                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                    }
                                                }
                                            }else{
                                                posicaoLinha -= 5;
                                                c = getChar();
                                                if(isChar(c)){
                                                    lexema = "";
                                                    lexema += verificaVar(c);
                                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                }else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                }
                                            }
                                        }else{
                                            posicaoLinha -= 4;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 3;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 2;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }
                        break;
                        case 'c':
                            if(proximoChar('a')){
                                if(proximoChar('r')){
                                    if(proximoChar('a')){
                                        if(proximoChar('c')){
                                            c = getChar();
                                            if(Simbolos.verificaSimbolo(c)){
                                                token = new Token(TipoToken.TIPOVAR, "carac", numeroLinha);
                                                posicaoLinha--;
                                            }else{
                                                posicaoLinha -=6;
                                                c = getChar();
                                                if(isChar(c)){
                                                    lexema = "";
                                                    lexema += verificaVar(c);
                                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                }else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                }
                                            }
                                        }else{
                                            posicaoLinha -= 5;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else{
                                posicaoLinha --;
                                if(proximoChar('o')){
                                    if(proximoChar('n')){
                                        if(proximoChar('s')){
                                            if(proximoChar('t')){
                                                c = getChar();
                                                if(Simbolos.verificaSimbolo(c)){
                                                    token = new Token(TipoToken.DECLARACAOCONSTINI, "const", numeroLinha);
                                                    posicaoLinha--;
                                                }else{
                                                    posicaoLinha -=6;
                                                    c = getChar();
                                                    if(isChar(c)){
                                                        lexema = "";
                                                        lexema += verificaVar(c);
                                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                    }
                                                }
                                            }else{
                                                posicaoLinha -= 5;
                                                c = getChar();
                                                if(isChar(c)){
                                                    lexema = "";
                                                    lexema += verificaVar(c);
                                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                }else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                }
                                            }
                                        }else{
                                            posicaoLinha -= 4;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 3;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 2;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }
                        break;
                        case 't':
                            if(proximoChar('r')){
                                if(proximoChar('u')){
                                    if(proximoChar('e')){
                                        c = getChar();
                                        if(Simbolos.verificaSimbolo(c)){
                                            token = new Token(TipoToken.VALTRUE, "true", numeroLinha);
                                            posicaoLinha--;
                                        }else{
                                            posicaoLinha -=5;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else{
                                posicaoLinha -= 2;
                                c = getChar();
                                if(isChar(c)){
                                    lexema = "";
                                    lexema += verificaVar(c);
                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                }
                            }
                        break;
                        case 'b':
                            if(proximoChar('o')){
                                if(proximoChar('l')){
                                    if(proximoChar('e')){
                                        if(proximoChar('a')){
                                            if(proximoChar('n')){
                                                c = getChar();
                                                if(Simbolos.verificaSimbolo(c)){
                                                    token = new Token(TipoToken.TIPOVAR, "bolean", numeroLinha);
                                                    posicaoLinha--;
                                                }else{
                                                    posicaoLinha -=7;
                                                    c = getChar();
                                                    if(isChar(c)){
                                                        lexema = "";
                                                        lexema += verificaVar(c);
                                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                    }else{
                                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                    }
                                                }
                                            }else{
                                                posicaoLinha -= 6;
                                                c = getChar();
                                                if(isChar(c)){
                                                    lexema = "";
                                                    lexema += verificaVar(c);
                                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                                }else{
                                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                                }
                                            }
                                        }else{
                                            posicaoLinha -= 5;
                                            c = getChar();
                                            if(isChar(c)){
                                                lexema = "";
                                                lexema += verificaVar(c);
                                                token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                            }else{
                                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                            }
                                        }
                                    }else{
                                        posicaoLinha -= 4;
                                        c = getChar();
                                        if(isChar(c)){
                                            lexema = "";
                                            lexema += verificaVar(c);
                                            token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                        }else{
                                            token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        }
                                    }
                                }else{
                                    posicaoLinha -= 3;
                                    c = getChar();
                                    if(isChar(c)){
                                        lexema = "";
                                        lexema += verificaVar(c);
                                        token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    }
                                }
                            }else{
                                posicaoLinha -= 2;
                                c = getChar();
                                if(isChar(c)){
                                    lexema = "";
                                    lexema += verificaVar(c);
                                    token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                }
                            }
                        break;
                        default:
                            if(isChar(c)){
                               lexema = verificaVar(c);
                               token = new Token(TipoToken.NOMEVAR, lexema, numeroLinha);
                               posicaoLinha --;
                            }
                            if(isDigito(c)){
                                String inteiro = "";
                                inteiro += c;
                                c = getChar();
                                if(isPonto(c)){
                                    String real = "";
                                    real = inteiro + c;
                                    c = getChar();
                                    while(isDigito(c)){
                                        real += c;
                                        c = getChar();
                                    }
                                    lexema += real;
                                    token = new Token(TipoToken.VALREAL, real, numeroLinha);
                                    posicaoLinha--;
                                }else{
                                    while (isDigito(c)){
                                        inteiro += c;
                                        c = getChar();
                                    }
                                    if(isPonto(c)){
                                        String real = "";
                                        real = inteiro + c;
                                        c = getChar();
                                        while(isDigito(c)){
                                            real += c;
                                            c = getChar();
                                        }
                                        lexema += real;
                                        token = new Token(TipoToken.VALREAL, real, numeroLinha);
                                        posicaoLinha--;
                                    }else{
                                        lexema += inteiro;
                                        token = new Token(TipoToken.VALINTEIRO, inteiro, numeroLinha);
                                        posicaoLinha--;
                                    }
                                }
                            }
                            if(isSpecial(c)){
                                //token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                            }
                            if (c == 0) {
                                token = new Token(TipoToken.EOF, "Erro final do arquivo", numeroLinha);
                            } else {
                                lexema += c; 
                            }
                        break;
                    }break;  
                    

            }//switch aut�mato
        }//while
    	return token;
    }
    public static void main(String[] args) {
		Analisador lexico = new Analisador();
		Token token;
		try {
			lexico.abreArquivo("teste.txt");
			token = lexico.capturaToken();
			while (token.getToken() != TipoToken.EOF) {
				System.out.println(token.toString());
				token = lexico.capturaToken();
			}
			System.out.println(token.toString());
			lexico.fechaArquivo("teste.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

	    


