/* 
 * Copyright (C) 2014 Francesco Pennica <francesco.pennica at igag.cnr.it>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

Ext.define('AUDB.view.allagamenti.ReverseGeocodingTrigger', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.reversegeocodingtrigger',

    

    initComponent: function () {
        var me = this;

        me.trigger2Cls = 'x-form-search-trigger';

        me.callParent(arguments);
    },

    // override onTriggerClick
    onTrigger2Click: function() {
        //Ext.Msg.alert('Status', 'You clicked geocodingtrigger!');
        this.setRawValue('');
        var me = this;
        //if (!me.hideTrigger) {
                me.fireEvent("triggerclick", me);
        //}
    }
});
