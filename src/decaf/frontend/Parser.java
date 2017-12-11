//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short CASE=285;
public final static short DEFAULT=286;
public final static short SUPER=287;
public final static short SCOPY=288;
public final static short DCOPY=289;
public final static short PRINTCOMP=290;
public final static short COMPLEX=291;
public final static short DO=292;
public final static short OD=293;
public final static short BRAN=294;
public final static short UMINUS=295;
public final static short EMPTY=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   14,   14,
   14,   26,   26,   23,   23,   25,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   28,
   28,   27,   27,   30,   30,   16,   17,   21,   15,   31,
   31,   18,   18,   19,   20,   29,   32,   32,   32,   34,
   33,   22,   22,   35,   35,   37,   36,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    2,    1,    1,    3,    1,
    0,    2,    0,    2,    4,    5,    1,    1,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    3,    3,    1,    4,    5,
    6,    5,    2,    2,    2,    4,    4,    1,    1,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,    4,    8,    2,    1,    0,    4,
    4,    4,    3,    2,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,    0,    0,   13,   10,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   81,   68,    0,    0,
    0,    0,   88,    0,    0,    0,    0,   80,    0,    0,
    0,    0,   25,    0,   78,    0,    0,    0,    0,    0,
    0,    0,   28,   37,   26,    0,   30,   31,   32,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   49,   79,
    0,    0,    0,   47,    0,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  105,    0,    0,    0,   29,   33,   34,
   35,   36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   66,   67,    0,    0,
   63,    0,    0,    0,    0,    0,    0,  104,  103,  106,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
    0,    0,   94,    0,    0,    0,   77,   76,   95,  107,
  102,   45,    0,    0,   86,    0,    0,   70,    0,    0,
   72,    0,   46,    0,    0,   89,   71,    0,    0,   98,
    0,   90,    0,    0,    0,   97,    0,    0,    0,   96,
   87,  100,    0,  101,
};
final static short yydgoto[] = {                          2,
    3,    4,   73,   21,   34,    8,   11,   23,   35,   36,
   74,   46,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,   94,   86,   96,   88,  193,   89,   90,  146,
  206,  209,  215,  210,  112,  113,  114,
};
final static short yysindex[] = {                      -210,
 -205,    0, -210,    0, -207,    0, -198,  -40,    0,    0,
  511,    0,    0,    0,    0, -183,  -84,    0,    0,    0,
   42,  -85,    0,    0,  -83,    0,   60,    9,   76,  -84,
    0,  -84,    0,  -74,   78,   73,   80,    0,   -1,  -84,
   -1,    0,    0,    0,    0,    5,    0,    0,   86,   94,
   95,  116,    0,  -90,   99,  100,  102,    0,  103,  116,
  116,   63,    0,  104,    0,  105,  107,  108,  116,  116,
  116,  116,    0,    0,    0,   91,    0,    0,    0,   98,
  106,  119,  120,    0,   92,  838,    0, -122,    0,    0,
  116,  116,  116,    0,  838,    0,  115,   71,  116,  122,
  125,  116,  -28,  -28,  -95,  499,  116,  116,  116,  116,
  510,  116, -269,    0,  838,  838,  838,    0,    0,    0,
    0,    0,  116,  116,  116,  116,  116,  116,  116,  116,
  116,  116,  116,  116,  116,  116,    0,  116,  142,  532,
  124,  562,  143,   96,  838,  -12,    0,    0,  573,  144,
    0,  613,  698,  758,   29,   41, -229,    0,    0,    0,
  838,  965,  953,   15,   15,  -32,  -32,   67,   67,  -28,
  -28,  -28,   15,   15,  816,  116,   41,  116,   41,    0,
  827,  116,    0,  -89,  116,   65,    0,    0,    0,    0,
    0,    0,  151,  159,    0,  868,  -64,    0,  838,  164,
    0, -240,    0,  116,   41,    0,    0,  148, -220,    0,
  167,    0,  116,  152,   84,    0,   41,  879,  116,    0,
    0,    0,  903,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  216,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  158,    0,    0,  178,
    0,  178,    0,    0,    0,  181,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -55,    0,    0,    0,    0,    0,    0,    0,  -53,
  -53,  -53,    0,    0,    0,    0,    0,    0,  -53,  -53,
  -53,  -53,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  941,    0,  472,    0,    0,    0,
  -53,  -58,  -53,    0,  171,    0,    0,    0,  -53,    0,
    0,  -53,  365,  401,    0,    0,  -53,  -53,  -53,  -53,
    0,  -53,    0,    0,   -5,   53,   66,    0,    0,    0,
    0,    0,  -53,  -53,  -53,  -53,  -53,  -53,  -53,  -53,
  -53,  -53,  -53,  -53,  -53,  -53,    0,  -53,  153,    0,
    0,    0,    0,  -53,   38,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    8,  374,  -25,   79,  913,  507,  570, 1002, 1010,  410,
  437,  446,  933,  990,    0,    6,  -58,  -53,  -58,    0,
    0,  -53,    0,    0,  -53,    0,    0,    0,    0,    0,
    0,    0,    0,  194,    0,    0,  -33,    0,   74,    0,
    0,  -41,    0,    7,  -58,    0,    0,    0,    0,    0,
    0,    0,  -53,    0,    0,    0,  -58,    0,  -53,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  245,  240,   55,   26,    0,    0,    0,  236,    0,
   50,    0, -133,  -71,    0,    0,    0,    0,    0,    0,
    0,    0,  521, 1237,  619,    0,    0, -182,    0, -101,
    0,    0,    0,   64,    0,  169,  172,
};
final static int YYTABLESIZE=1456;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         91,
   41,   91,   91,   93,  134,   28,   91,   28,  155,  132,
  130,   91,  131,  137,  133,   62,   28,  137,   62,  208,
  141,   47,  190,  159,  160,   91,  208,  136,  183,  135,
   91,  182,   62,   62,   58,   73,   22,   61,   73,   70,
   72,   47,   25,  195,   62,  197,   83,   41,   39,   60,
    1,  134,   73,   73,   58,    7,  132,  130,  138,  131,
  137,  133,  138,  191,  160,  214,   39,   62,   71,  189,
    5,  212,  182,   61,  194,   70,   72,    9,   85,   98,
   62,   85,   10,  221,   33,   60,   33,   73,   43,   91,
   45,   91,   24,   74,   44,   61,   74,   70,   72,   30,
   26,   31,   62,  134,   71,  138,   75,   60,  132,   75,
   74,   74,  137,  133,   84,   32,   40,   84,   39,   59,
   41,   42,   59,   75,   75,   91,   71,   42,   61,   63,
   70,   72,  211,   92,   93,   62,   59,   59,   99,  100,
   60,  101,  102,  107,  108,   74,  109,  110,   61,  118,
   70,   72,  123,  139,  143,   62,  119,  138,   75,   71,
   60,  144,  147,   42,  120,  148,   12,   13,   14,   15,
   16,   59,   12,   13,   14,   15,   16,  121,  122,   71,
  150,  176,  178,  180,  185,   97,  200,  202,   31,   44,
   27,  203,   29,   44,   44,   44,   44,   44,   44,   44,
   19,   38,  182,  205,  207,  213,   19,  217,  220,  219,
   44,   44,   44,   44,   44,    1,    5,   43,   20,   15,
   43,   19,   43,   91,   91,   91,   91,   91,   91,   92,
   91,   91,   91,   91,   82,   91,   91,   91,   91,   91,
   91,   91,   91,   44,   99,   44,   91,    6,  126,  127,
   20,   91,   62,   91,   91,   91,   91,   91,   91,   91,
   91,   12,   13,   14,   15,   16,   47,   37,   48,   49,
   50,   51,  216,   52,   53,   54,   55,   56,   57,   58,
  157,   43,   43,  158,   59,    0,    0,    0,    0,   64,
    0,   65,   66,   67,   68,   19,   69,   12,   13,   14,
   15,   16,   47,    0,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,    0,    0,    0,    0,
   59,    0,    0,  105,   47,   64,   48,   65,   66,   67,
   68,   19,   69,   54,    0,   56,   57,   58,    0,    0,
    0,    0,   59,    0,    0,    0,    0,   64,    0,   65,
   66,   67,    0,    0,    0,   59,   59,   47,    0,   48,
    0,   59,   59,    0,    0,    0,   54,    0,   56,   57,
   58,    0,    0,    0,    0,   59,    0,   47,    0,   48,
   64,    0,   65,   66,   67,    0,   54,    0,   56,   57,
   58,    0,    0,    0,    0,   59,    0,    0,    0,    0,
   64,   64,   65,   66,   67,   64,   64,   64,   64,   64,
    0,   64,    0,    0,   61,    0,    0,   61,    0,    0,
    0,    0,   64,   64,   64,    0,   64,    0,    0,   44,
   44,   61,   61,   44,   44,   44,   44,   65,    0,    0,
    0,   65,   65,   65,   65,   65,   52,   65,    0,    0,
   52,   52,   52,   52,   52,    0,   52,   64,   65,   65,
   65,    0,   65,    0,    0,    0,   61,   52,   52,   52,
    0,   52,    0,   53,    0,    0,    0,   53,   53,   53,
   53,   53,   54,   53,    0,    0,   54,   54,   54,   54,
   54,    0,   54,   65,   53,   53,   53,    0,   53,    0,
    0,    0,   52,   54,   54,   54,    0,   54,   48,    0,
    0,    0,   40,   48,   48,    0,   48,   48,   48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   40,   48,    0,   48,    0,  134,    0,    0,   54,  151,
  132,  130,    0,  131,  137,  133,  134,   55,    0,    0,
   55,  132,  130,    0,  131,  137,  133,    0,  136,    0,
  135,    0,   48,    0,   55,   55,   85,  156,  134,  136,
    0,  135,  177,  132,  130,    0,  131,  137,  133,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  138,
    0,  136,    0,  135,    0,    0,    0,    0,  134,   55,
  138,    0,  179,  132,  130,    0,  131,  137,  133,  134,
   56,    0,   85,   56,  132,  130,  184,  131,  137,  133,
    0,  136,  138,  135,    0,    0,    0,   56,   56,    0,
    0,    0,  136,    0,  135,   18,    0,    0,    0,    0,
    0,   64,   64,    0,    0,   64,   64,   64,   64,  134,
   61,   61,  138,  186,  132,  130,    0,  131,  137,  133,
    0,    0,   56,  138,   87,    0,    0,    0,    0,    0,
    0,    0,  136,    0,  135,    0,   85,   65,   65,    0,
    0,   65,   65,   65,   65,    0,   52,   52,    0,    0,
   52,   52,   52,   52,    0,    0,    0,   85,    0,   85,
    0,    0,    0,  138,    0,    0,    0,    0,    0,    0,
   87,    0,    0,   53,   53,    0,    0,   53,   53,   53,
   53,    0,   54,   54,   85,   85,   54,   54,   54,   54,
    0,    0,    0,    0,  134,    0,    0,   85,  187,  132,
  130,    0,  131,  137,  133,    0,    0,    0,   48,   48,
    0,    0,   48,   48,   48,   48,    0,  136,    0,  135,
    0,    0,    0,    0,    0,    0,    0,   12,   13,   14,
   15,   16,    0,    0,   87,  124,  125,    0,    0,  126,
  127,  128,  129,   55,   55,    0,  124,  125,  138,   17,
  126,  127,  128,  129,  134,   87,    0,   87,  188,  132,
  130,   19,  131,  137,  133,    0,    0,    0,  124,  125,
    0,    0,  126,  127,  128,  129,    0,  136,    0,  135,
    0,    0,   87,   87,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,    0,  124,  125,
    0,    0,  126,  127,  128,  129,   56,   56,  138,  124,
  125,    0,  134,  126,  127,  128,  129,  132,  130,    0,
  131,  137,  133,  134,    0,    0,    0,    0,  132,  130,
    0,  131,  137,  133,  134,  136,    0,  135,    0,  132,
  130,    0,  131,  137,  133,    0,  136,    0,  135,  124,
  125,    0,    0,  126,  127,  128,  129,  136,    0,  135,
    0,    0,    0,    0,  134,    0,  138,    0,  192,  132,
  130,    0,  131,  137,  133,  134,    0,  138,    0,  198,
  132,  130,    0,  131,  137,  133,  204,  136,  138,  135,
    0,    0,    0,    0,    0,    0,    0,  222,  136,  134,
  135,    0,    0,    0,  132,  130,    0,  131,  137,  133,
    0,    0,    0,   60,    0,    0,   60,    0,  138,    0,
    0,  224,  136,    0,  135,    0,    0,    0,    0,  138,
   60,   60,    0,   58,  124,  125,   58,   47,  126,  127,
  128,  129,   47,   47,    0,   47,   47,   47,    0,  134,
   58,   58,    0,  138,  132,  130,    0,  131,  137,  133,
   47,  134,   47,    0,    0,   60,  132,  130,    0,  131,
  137,  133,  136,    0,  135,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  136,   58,  135,    0,    0,    0,
   57,   47,    0,   57,  124,  125,    0,    0,  126,  127,
  128,  129,   50,  138,   50,   50,   50,   57,   57,    0,
   51,    0,   51,   51,   51,  138,    0,    0,    0,   50,
   50,   50,    0,   50,    0,    0,    0,   51,   51,   51,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  124,  125,   50,    0,  126,  127,  128,  129,
    0,    0,   51,  124,  125,    0,    0,  126,  127,  128,
  129,    0,    0,    0,  124,  125,    0,    0,  126,  127,
  128,  129,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  124,  125,    0,    0,  126,  127,
  128,  129,    0,    0,    0,  124,  125,    0,    0,  126,
  127,  128,  129,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  124,
  125,    0,    0,  126,  127,  128,  129,    0,    0,   60,
   60,    0,    0,    0,    0,   60,   60,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
   58,    0,    0,    0,    0,   58,   58,   47,   47,    0,
    0,   47,   47,   47,   47,    0,    0,    0,    0,  124,
    0,    0,    0,  126,  127,  128,  129,    0,    0,    0,
    0,    0,    0,    0,    0,  126,  127,  128,  129,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   57,   57,    0,    0,
    0,    0,   57,   57,    0,    0,    0,    0,   50,   50,
    0,    0,   50,   50,   50,   50,   51,   51,   95,    0,
   51,   51,   51,   51,    0,    0,  103,  104,  106,    0,
    0,    0,    0,    0,    0,  111,  115,  116,  117,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  140,    0,  142,
    0,    0,    0,    0,    0,  145,    0,    0,  149,    0,
    0,    0,    0,  152,  153,  154,  145,    0,  111,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  161,
  162,  163,  164,  165,  166,  167,  168,  169,  170,  171,
  172,  173,  174,    0,  175,    0,    0,    0,    0,    0,
  181,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  145,    0,  196,    0,    0,    0,  199,    0,
    0,  201,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  218,
    0,    0,    0,    0,    0,  223,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   59,   37,   91,   40,   91,  110,   42,
   43,   45,   45,   46,   47,   41,   91,   46,   44,  202,
   92,  262,  156,  293,  294,   59,  209,   60,   41,   62,
   64,   44,   58,   59,  275,   41,   11,   33,   44,   35,
   36,  262,   17,  177,   40,  179,   41,   41,   41,   45,
  261,   37,   58,   59,  275,  263,   42,   43,   91,   45,
   46,   47,   91,  293,  294,  286,   59,   93,   64,   41,
  276,  205,   44,   33,  176,   35,   36,  276,   41,   54,
   40,   44,  123,  217,   30,   45,   32,   93,   39,  123,
   41,  125,  276,   41,   40,   33,   44,   35,   36,   40,
   59,   93,   40,   37,   64,   91,   41,   45,   42,   44,
   58,   59,   46,   47,   41,   40,   44,   44,   41,   41,
   41,  123,   44,   58,   59,   40,   64,  123,   33,  125,
   35,   36,  204,   40,   40,   40,   58,   59,   40,   40,
   45,   40,   40,   40,   40,   93,   40,   40,   33,   59,
   35,   36,   61,  276,   40,   40,   59,   91,   93,   64,
   45,   91,   41,  123,   59,   41,  257,  258,  259,  260,
  261,   93,  257,  258,  259,  260,  261,   59,   59,   64,
  276,   40,   59,   41,   41,  276,  276,  123,   93,   37,
  276,   41,  276,   41,   42,   43,   44,   45,   46,   47,
  291,  276,   44,  268,   41,   58,  291,   41,  125,   58,
   58,   59,   60,   61,   62,    0,   59,  276,   41,  123,
  276,   41,  276,  257,  258,  259,  260,  261,  262,   59,
  264,  265,  266,  267,   41,  269,  270,  271,  272,  273,
  274,  275,  276,   91,  286,   93,  280,    3,  281,  282,
   11,  285,  278,  287,  288,  289,  290,  291,  292,  293,
  294,  257,  258,  259,  260,  261,  262,   32,  264,  265,
  266,  267,  209,  269,  270,  271,  272,  273,  274,  275,
  112,  276,  276,  112,  280,   -1,   -1,   -1,   -1,  285,
   -1,  287,  288,  289,  290,  291,  292,  257,  258,  259,
  260,  261,  262,   -1,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,
  280,   -1,   -1,  261,  262,  285,  264,  287,  288,  289,
  290,  291,  292,  271,   -1,  273,  274,  275,   -1,   -1,
   -1,   -1,  280,   -1,   -1,   -1,   -1,  285,   -1,  287,
  288,  289,   -1,   -1,   -1,  277,  278,  262,   -1,  264,
   -1,  283,  284,   -1,   -1,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,   -1,  262,   -1,  264,
  285,   -1,  287,  288,  289,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,
  285,   37,  287,  288,  289,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,
   -1,   -1,   58,   59,   60,   -1,   62,   -1,   -1,  277,
  278,   58,   59,  281,  282,  283,  284,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   37,   47,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   93,   58,   59,
   60,   -1,   62,   -1,   -1,   -1,   93,   58,   59,   60,
   -1,   62,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   37,   47,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   93,   58,   59,   60,   -1,   62,   -1,
   -1,   -1,   93,   58,   59,   60,   -1,   62,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   59,   60,   -1,   62,   -1,   37,   -1,   -1,   93,   41,
   42,   43,   -1,   45,   46,   47,   37,   41,   -1,   -1,
   44,   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,
   62,   -1,   91,   -1,   58,   59,   46,   58,   37,   60,
   -1,   62,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   37,   93,
   91,   -1,   41,   42,   43,   -1,   45,   46,   47,   37,
   41,   -1,   92,   44,   42,   43,   44,   45,   46,   47,
   -1,   60,   91,   62,   -1,   -1,   -1,   58,   59,   -1,
   -1,   -1,   60,   -1,   62,  125,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   37,
  277,  278,   91,   41,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   93,   91,   46,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,  156,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  177,   -1,  179,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,
   92,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,  277,  278,  204,  205,  281,  282,  283,  284,
   -1,   -1,   -1,   -1,   37,   -1,   -1,  217,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,   -1,   -1,  156,  277,  278,   -1,   -1,  281,
  282,  283,  284,  277,  278,   -1,  277,  278,   91,  279,
  281,  282,  283,  284,   37,  177,   -1,  179,   41,   42,
   43,  291,   45,   46,   47,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   60,   -1,   62,
   -1,   -1,  204,  205,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  217,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,  277,  278,   91,  277,
  278,   -1,   37,  281,  282,  283,  284,   42,   43,   -1,
   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   37,   60,   -1,   62,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,  277,
  278,   -1,   -1,  281,  282,  283,  284,   60,   -1,   62,
   -1,   -1,   -1,   -1,   37,   -1,   91,   -1,   93,   42,
   43,   -1,   45,   46,   47,   37,   -1,   91,   -1,   93,
   42,   43,   -1,   45,   46,   47,   59,   60,   91,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   37,
   62,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   91,   -1,
   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,
   58,   59,   -1,   41,  277,  278,   44,   37,  281,  282,
  283,  284,   42,   43,   -1,   45,   46,   47,   -1,   37,
   58,   59,   -1,   91,   42,   43,   -1,   45,   46,   47,
   60,   37,   62,   -1,   -1,   93,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   93,   62,   -1,   -1,   -1,
   41,   91,   -1,   44,  277,  278,   -1,   -1,  281,  282,
  283,  284,   41,   91,   43,   44,   45,   58,   59,   -1,
   41,   -1,   43,   44,   45,   91,   -1,   -1,   -1,   58,
   59,   60,   -1,   62,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   93,   -1,  281,  282,  283,  284,
   -1,   -1,   93,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,  277,
   -1,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,  277,  278,   52,   -1,
  281,  282,  283,  284,   -1,   -1,   60,   61,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   69,   70,   71,   72,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   99,   -1,   -1,  102,   -1,
   -1,   -1,   -1,  107,  108,  109,  110,   -1,  112,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
  124,  125,  126,  127,  128,  129,  130,  131,  132,  133,
  134,  135,  136,   -1,  138,   -1,   -1,   -1,   -1,   -1,
  144,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  176,   -1,  178,   -1,   -1,   -1,  182,   -1,
   -1,  185,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  213,
   -1,   -1,   -1,   -1,   -1,  219,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","CASE",
"DEFAULT","SUPER","SCOPY","DCOPY","PRINTCOMP","COMPLEX","DO","OD","BRAN",
"UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : DoStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : '#' Expr",
"Expr : '@' Expr",
"Expr : '$' Expr",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : SUPER",
"Expr : CaseExpr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
"CaseExpr : CASE '(' Expr ')' '{' CaseList DefaultExpr '}'",
"CaseList : CaseList ACaseEXor",
"CaseList : ACaseEXor",
"CaseList :",
"ACaseEXor : Constant ':' Expr ';'",
"DefaultExpr : DEFAULT ':' Expr ';'",
"DoStmt : DO DoBranchList DoSubStmt OD",
"DoStmt : DO DoSubStmt OD",
"DoBranchList : DoBranchList DoBranch",
"DoBranchList : DoBranch",
"DoBranch : DoSubStmt BRAN",
"DoSubStmt : Expr ':' Stmt",
};

//#line 525 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 723 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 61 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 67 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 71 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 81 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 87 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 103 "Parser.y"
{
                        yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                    }
break;
case 11:
//#line 107 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 111 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 117 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 123 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 127 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 133 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 137 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 149 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 156 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 160 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 167 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 171 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 177 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 183 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 187 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 194 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 199 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 216 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 40:
//#line 220 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 41:
//#line 224 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 231 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 44:
//#line 237 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 45:
//#line 244 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 47:
//#line 259 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 50:
//#line 265 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 269 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 317 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 64:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 68:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 69:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 70:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 71:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 72:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 73:
//#line 357 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 74:
//#line 361 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 75:
//#line 365 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 76:
//#line 369 "Parser.y"
{
                        yyval.expr = new Tree.Dcopy(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 77:
//#line 373 "Parser.y"
{
                        yyval.expr = new Tree.Scopy(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 78:
//#line 377 "Parser.y"
{
                        yyval.expr = new Tree.Super(val_peek(0).loc);
                    }
break;
case 80:
//#line 384 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 388 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 395 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 402 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 406 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 413 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 87:
//#line 419 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 88:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 89:
//#line 431 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 90:
//#line 437 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 91:
//#line 441 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 92:
//#line 447 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 93:
//#line 451 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 94:
//#line 457 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 95:
//#line 462 "Parser.y"
{
                        yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
                    }
break;
case 96:
//#line 467 "Parser.y"
{
                        yyval.expr = new Tree.Case(val_peek(5).expr, val_peek(2).slist, val_peek(1).expr, val_peek(7).loc);
                    }
break;
case 97:
//#line 471 "Parser.y"
{ yyval.slist.add(val_peek(0).stmt); }
break;
case 98:
//#line 473 "Parser.y"
{
                        yyval.slist = new ArrayList<Tree>();
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 99:
//#line 478 "Parser.y"
{ yyval = new SemValue(); }
break;
case 100:
//#line 482 "Parser.y"
{
                        yyval.stmt = new Tree.CaseItem(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 101:
//#line 487 "Parser.y"
{ 
                        yyval.expr = val_peek(1).expr; 
                    }
break;
case 102:
//#line 492 "Parser.y"
{
                        val_peek(2).slist.add(val_peek(1).stmt);
                        yyval.stmt = new Tree.Dood(val_peek(2).slist, val_peek(3).loc);
                    }
break;
case 103:
//#line 497 "Parser.y"
{
                        val_peek(1).slist = new ArrayList<Tree>();
                        val_peek(1).slist.add(val_peek(1).stmt);
                        yyval.stmt = new Tree.Dood(val_peek(1).slist, val_peek(2).loc);
                    }
break;
case 104:
//#line 504 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 105:
//#line 508 "Parser.y"
{
                        yyval.slist = new ArrayList<Tree>();
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 106:
//#line 514 "Parser.y"
{
                        yyval.stmt = val_peek(1).stmt;
                    }
break;
case 107:
//#line 519 "Parser.y"
{
                        yyval.stmt = new Tree.DoodBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
                    }
break;
//#line 1431 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
