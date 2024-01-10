<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : newstylesheet.xsl
    Created on : 17 octobre 2023, 17:02
    Author     : dingzi
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tux="http://myGame/tux"
                version="1.0">
    
    
    
    <xsl:output method="html"/>
    
    
    
    
    <!--
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>dico</title>
            </head>
            <body>
                <title>Liste de mots :</title>
                <table>
                    <tr>
                        <th>Mot(s)</th>
                        <th>Niveau</th>
                    </tr>
                    <xsl:apply-templates select="//tux:mot">
                        <xsl:sort select="." order="ascending"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>
    
    
    
    
    <!--On affiche les mots triés-->
    <xsl:template match="tux:mot">
        <tr>
            <td> <xsl:value-of select="."/> </td>
            <td> <xsl:value-of select="@niveau"/> </td>
        </tr>
    </xsl:template>
    
    
    
    
</xsl:stylesheet>
