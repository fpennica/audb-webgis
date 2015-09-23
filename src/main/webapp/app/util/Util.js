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

Ext.define('AUDB.util.Util', {
    
    statics: {
        
        required: '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
        
        decodeJson: function(text) {
            var result = Ext.JSON.decode(text, true);
            if (!result) {
                result = {};
                result.success = false;
                result.msg = text;
            }
            return result;
        },
        
        showErrorMsg: function(text) {
            Ext.Msg.show({
                title: 'Error!',
                msg: text,
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK
            });
        }
        
    }
});
