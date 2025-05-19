#MiniLang Compiler – Compilador en Java
##Estudiantes: Juan García Duque, Miguel Angel lopez y Juan Felipe Vanegas 
**Profesor**: Alex Atehortúa  
## Descripción
Este proyecto es un compilador funcional en Java para un lenguaje de programación propio llamado MiniLang.  
El compilador realiza todas las fases tradicionales:
- 🔤 Análisis léxico
- 🧱 Análisis sintáctico
- 🧠 Análisis semántico
- 🚀 Ejecución del código
##Características de MiniLang
MiniLang es un lenguaje sencillo que permite:
- Declarar variables con "let".  
  ```txt
  let x = 5;
- Realizar operaciones aritméticas: +, -, *, /
- Imprimir los variables con print(x);

## Cómo ejecutar
- Asegúrate de tener Java 17 o superior instalado.
- clona el repositorio
- Pega los codigos siguiendo la estructura anterior
- Coloca tu código MiniLang en src/main/resources/MiniLang.txt
- Corra el código
- Resultado esperado:
  Sintaxis válida.
  Análisis semántico exitoso.
  y = 15


## Fases implementadas
- Léxica	Lexer.java	Convierte texto en tokens.
-  Sintáctica	Parser.java	Verifica estructura del código
-  Semántica	SemanticAnalyzer.java	Valida lógica (variables declaradas)
-  Ejecución	Executor.java	Interpreta y ejecuta el código fuente

## Relación con Teoría de la Computación
Este proyecto aplica directamente los conceptos vistos en clase:
- Autómatas Finitos: Usados en el análisis léxico (detectar palabras clave, identificadores y números)
- Gramáticas Libres de Contexto (GLC): Aplicadas en el parser para validar estructura como let x = y + 2;
- Lenguajes Formales: MiniLang es un lenguaje diseñado con reglas léxicas y sintácticas claras
- Fases del compilador: Implementadas completamente, desde lexer hasta ejecución

## Mejoras
- Intérprete funcional (fase de ejecución)
- Validación de duplicados y uso de variables
- Soporte para operaciones aritméticas básicas
- Código modular por fases




