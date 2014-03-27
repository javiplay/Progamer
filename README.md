code-reimagined: Progamer
=========================

Main repository to maintain Code Reimagined

Code Reimagined es un plugin para Eclipse (para Java). 

Básicamente ofrece una vista en el entorno de Eclipse donde podemos observar una representación visual gráfica 
del código que se está escribiendo. Esta representación gráfica consiste en identificar los elementos del código 
Java como elementos gráficos que componen un escenario típico de un videojuego de plataformas como es Super Maryo. 
De esta forma al ejecutar paso a paso el programa podremos observar al personaje protagonista del videojuego 
recorriendo este escenario. 

Los elementos de código básicos y su correspondencia gráfica son:

 - métodos: son plataformas en la base del escenario.
 - expresiones: son las cajas amarillas normales que Super Maryo rompe.
 - declaraciones: son cajas con interrogación.
 - Sentencia IF: es una plataforma elevada, si la condición se cumple Super Maryo salta a la plataforma.
 - Sentencia SWITCH: son varias plataformas (una por cada CASE) una sobre otra, Super Maryo irá al CASE que se cumpla.
 - Bucle FOR: son dos tubos, uno al principio del bloque y otro al final, por donde Maryo vuelve al inicio del bucle.
 - Sentencia RETURN: es una puerta por donde Maryo sale del escenario.

Además de la visualización del escenario, podemos usar la vista para hacer doble clic sobre sus elementos lo que 
posicionará el cursor en la línea de código correspondiente del editor de Java. De igual forma al mover el cursor
en el código, Maryo aparecerá en el lugar correspondiente del escenario.

El plugin es fácilmente extensible, simplemente hay que crear una clase para el elemento Java que se quiere representar
(extendiendo del elemento base) donde se implementará el método getSprites(x,y) para indicar la forma de pintarlo.

La principal utilidad de este plugin es facilitar el aprendizaje de la programación en edades tempranas.
Otra utilidad puede ser la documentación de código (usando representación de diagramas en lugar de videojuegos).

Para probarlo, se abre el proyecto en Eclipse y se ejecuta como aplicación de Eclipse. Una vez abierto el nuevo eclipse, 
se ha de mostrar la vista mediante el menú Show view-> Maryo Category-> Maryo View. Se crea un proyecto de Java y se 
añade una clase con código. En la vista aparecerá la representación gráfica cada vez que se guarde el archivo.

Gracias por visitar este proyecto.
Javier Asensio.


