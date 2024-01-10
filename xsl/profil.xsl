<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : profil.xsl
    Created on : 2023年12月2日, 04:18
    Author     : ding
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:tux="http://myGame/tux"
                version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>profil</title>
            </head>
            <body>
                <h3>Profile de <xsl:value-of select="//tux:nom"/></h3>
                <p><xsl:value-of select="//tux:anniversaire"/></p>
                
                <table>
                    <tr>
                        <th>Date</th>
                        <th>Mot</th>
                        <th>Temps</th>
                        <th>Niveau</th>
                        <th>Trouvé à(si pas fini)</th>
                    </tr>


                    <xsl:apply-templates select="//tux:partie">
                        <xsl:sort select="./tux:mot/@niveau" order="descending"/>
                        <xsl:sort select="./tux:mot" order="ascending"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="tux:partie">
        <tr>
            <td> <xsl:value-of select="@date"/> </td>
            <td> <xsl:value-of select="./tux:mot"/> </td>
            <td> <xsl:value-of select="./tux:temps"/> </td>
            <td> <xsl:value-of select="./tux:mot/@niveau"/> </td>
            <td> <xsl:value-of select="@trouvé"/> </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
