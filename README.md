Para executar o programa é necessário o Maven instalado na sua máquina ou alguma IDE que já venha com ele por default.
Executar a classes Main.java.

As matrizes estão em: src/main/resources/matrizes;
Os arquivos de saida são gerados em: src/main/resources/resultado;

Via CLI pode ser executado da raiz com:

```Bash
mvn -q clean compile
```

Utilizando por exemplo a matriz 4x4 com ponto vertice inicial 0 e vertice final 8:
```Bash
mvn exec:java -Dexec.args="matrix_4x4.txt 0 8
```
