function HashMap(){this._length=0;this._arrayControle=new Array();this.set=_set;this.get=_get;this.setArray=_setArray;this.remove=_remove;this.toArray=_toArray;this.length=_flength;function _set(id,object){this[id]=object;object.idHashMapInternalControl=id;this._arrayControle[this._length]=object;this._length++;}
function _get(id){return this[id];}
function _toArray(){return this._arrayControle;}
function _remove(id){var array=this.toArray();for(var i=0;i<array.length;i++){if(array[i].idHashMapInternalControl==id){this._arrayControle.splice(i,1);this._length--;}}
delete this[id];}
function _flength(){return this._length;}
function _setArray(name,propIdent,arr){if(arr==null||arr==undefined){return;}
for(var i=0;i<arr.length;i++){var obj=arr[i];this.set(name+obj[propIdent],obj);}}}