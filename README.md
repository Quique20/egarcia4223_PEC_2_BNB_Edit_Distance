# Enunciado
Dadas dos cadenas de caracteres X e Y, definidas sobre un alfabeto finito, se desea determinar
las modificaciones de coste total mínimo necesarias para transformar X = x1, x2, …, x n con
longitud n en Y = y1, y2, …, ym con longitud m.
Para realizar la transformación se permiten las siguientes operaciones:
    Borrado: eliminar un carácter de X, con coste cb.
    Inserción: añadir un carácter a Y, con coste ci.
    Sustitución: reemplazar un carácter de X por otro, con coste cs.
    Intercambio: cambia la posición de un carácter de X por la posición de otro en X, con coste cc.
La distancia de edición entre dos cadenas se define como el menor coste para convertir una
cadena en la otra. Por ejemplo, para transformar sacos en caso son necesarias como mínimo
dos operaciones:
1. sacos → casos — intercambio de los caracteres s y c.
2. casos → caso — borrado de s.
Por tanto, su distancia de edición es vendrá determinada por la suma de los costes cc + cb. Ten
en cuenta que el número de pasos no determina la distancia de edición, sino la suma de los
costes.