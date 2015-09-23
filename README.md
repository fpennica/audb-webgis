# audb-webgis
WebGIS for the management of the Urban Floods Database (AUDB)

![screen1](http://www.urbisit.it/images/audb/2014-12-16_14_35_10-AUDB.png "AUDB WebGIS interface")

This is a WebGIS application for the visualization and management of urban floods data collected from field observations and reports, and stored in a specialized section of the BDGT geodatabase developed by CNR-IGAG.

The WebGIS has been developed with the aim of centralizing and simplifying the management of flood data required for the development of scientific models for urban flood susceptibility. The studies are being carried out by CNR-IGAG in collaboration with the italian Civil Protection Department (DPC).

### Technologies
The AUDB WebGIS has been developed in 2014 using the following client-side javascript libraries:
* Sencha ExtJS 4.2.1
* OpenLayers 2.13.1
* GeoExt 2.0.2

On server-side:
* Java 8
* Spring 4.0.6
* Glassfish 4.1 with EclipseLink JPA implementation
* PostgreSQL 9.2 with PostGIS 2
* GeoServer for WMS layers

Visit the [Wiki](https://github.com/fpennica/audb-webgis/wiki) for more info.

Floods table

![screen2](http://www.urbisit.it/images/audb/2014-12-16_14_42_08-AUDB.png "Floods table")


Detail view

![screen3](http://www.urbisit.it/images/audb/2014-12-16_14_52_35-AUDB.png "Detail view")


Street view

![screen4](http://www.urbisit.it/images/audb/2014-12-16_14_56_25-AUDB.png "Street view")


Data editing

![screen5](http://www.urbisit.it/images/audb/2014-12-16_15_20_37-AUDB.png "Data editing")


User management

![screen6](http://www.urbisit.it/images/audb/2014-12-16_15_31_29-AUDB.png "User management")
