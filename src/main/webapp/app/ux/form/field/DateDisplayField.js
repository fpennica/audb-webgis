/*
 * https://ahlearns.wordpress.com/2014/04/10/ext-js-4-date-display-field/
 */
Ext.define('AUDB.ux.form.field.DateDisplayField', {
    extend: 'Ext.form.field.Display',
    
    alias: 'widget.datedisplayfield',
    
    datePattern: 'Y-m-d',
    
    valueToRaw: function (value) {
        return Ext.util.Format.date(value, this.datePattern);
    }
    
});
