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
        return c>=0 && c<=9;
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
                                                    token = new Token(TipoToken.PROGRAMAINICIO, "<prog ", numeroLinha);
                                                    
//                                                    c = getChar();
//                                                    if(isChar(c)){
//                                                        lexema += c;
//                                                         c = getChar();
//                                                        while (isChar(c) || isDigito(c) || c == '_') {                                                           
//                                                           lexema += c;
//                                                           c = getChar();
//                                                        }
//                                                   }
//                                                   posicaoLinha --;
//                                                   if(proximoChar('>')){
//                                                       token = new Token(TipoToken.PROGRAMAINICIO, "<prog "+lexema+">", numeroLinha);
//                                                       lexema = "";
//                                                       testaProg = false;
//                                                   }else{
//                                                       token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
//                                                       posicaoLinha --;
//                                                   }
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
                                    token = new Token(TipoToken.DECLARACAOVARINI, "var", numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    posicaoLinha --;
                                }
                            }else {
                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                posicaoLinha --;
                            }
                        break;
                        case 'i':
                            if(proximoChar('n')){
                                if(proximoChar('t')){
                                    token = new Token(TipoToken.TIPOVAR, "int", numeroLinha);
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    posicaoLinha --;
                                }
                            }else{
                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                posicaoLinha --;
                            }
                        break;
                        case 'f':
                            if(proximoChar('l')){
                                if(proximoChar('u')){
                                    if(proximoChar('t')){
                                        token = new Token(TipoToken.TIPOVAR, "flut", numeroLinha);
                                    }else{
                                        token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                        posicaoLinha --;
                                    }
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    posicaoLinha--;
                                }
                            }else{
                                posicaoLinha --;
                                if(proximoChar('a')){
                                    if(proximoChar('l')){
                                        if(proximoChar('s')){
                                            if(proximoChar('e')){
                                                token = new Token(TipoToken.VALFALSE, "false", numeroLinha);
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
                            }
                        break;
                        case 'c':
                            if(proximoChar('a')){
                                if(proximoChar('r')){
                                    if(proximoChar('a')){
                                        if(proximoChar('c')){
                                            token = new Token(TipoToken.TIPOVAR, "carac", numeroLinha);
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
                            }else{
                                posicaoLinha --;
                                if(proximoChar('o')){
                                    if(proximoChar('n')){
                                        if(proximoChar('s')){
                                            if(proximoChar('t')){
                                                token = new Token(TipoToken.DECLARACAOCONSTINI, "const", numeroLinha);
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
                            }
                        break;
                        case 't':
                            if(proximoChar('r')){
                                if(proximoChar('u')){
                                    if(proximoChar('e')){
                                        token = new Token(TipoToken.VALTRUE, "true", numeroLinha);
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
                        case 'b':
                            if(proximoChar('o')){
                                if(proximoChar('l')){
                                    if(proximoChar('e')){
                                        if(proximoChar('a')){
                                            if(proximoChar('n')){
                                                token = new Token(TipoToken.TIPOVAR, "bolean", numeroLinha);
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
                                }else{
                                    token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                    posicaoLinha --;
                                }
                            }else{
                                token = new Token(TipoToken.ERRO, "Palavra não reconhecida", numeroLinha);
                                posicaoLinha --;
                            }
                        break;
                        default:
                            if(isChar(c)){
                                String id = "";
                                id += c;
                                c = getChar();
                                while (isChar(c) || isDigito(c) || c == '_') {                                                           
                                    id += c;
                                    c = getChar();
                               }
                               lexema += id;
                               token = new Token(TipoToken.NOMEVAR, id, numeroLinha);
                               posicaoLinha --;
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

	    


