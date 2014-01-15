var UploadUtils_id_iframe='';var UploadUtils_id_elemento='';var UploadUtils_Action_Anterior='';function $m(quem){return document.getElementById(quem)}
function remove(quem){quem.parentNode.removeChild(quem);}
function addEvent(obj,evType,fn){if(obj.addEventListener)
obj.addEventListener(evType,fn,true)
if(obj.attachEvent)
obj.attachEvent("on"+evType,fn)}
function removeEvent(obj,type,fn){if(obj.detachEvent){obj.detachEvent('on'+type,fn);}else{obj.removeEventListener(type,fn,false);}}
function uploadFile(form,url_action,id_elemento_retorno,html_exibe_carregando,html_erro_http,id_iframe,id_campo_file){var erro="";var hiddenArquivo=$m('upFileNameHidden');var campoArquivo=$m(id_campo_file);var nomeArquivo=campoArquivo.value;hiddenArquivo.value=nomeArquivo;if($m(id_elemento_retorno)==null){erro+="O elemento passado no como parametro nao existe na pagina.\n";}
if(erro.length>0){alert("Erro ao chamar a função uploadFile:\n"+erro);return;}
if(id_iframe==null||id_iframe==''){alert("Informe o id do iframe!");return;}
$m(id_elemento_retorno).style.display='block';var carregou=function(){form.action=UploadUtils_Action_Anterior;removeEvent($m(id_iframe),"load",carregou);$m(id_elemento_retorno).style.display='none';}
addEvent($m(id_iframe),"load",carregou);UploadUtils_Action_Anterior=form.action;form.setAttribute("target",id_iframe);form.setAttribute("action",url_action);form.setAttribute("method","post");form.setAttribute("enctype","multipart/form-data");form.setAttribute("encoding","multipart/form-data");form.submit();form.action=UploadUtils_Action_Anterior;if(html_exibe_carregando.length>0){$m(id_elemento_retorno).innerHTML=html_exibe_carregando;}
setTimeout(function(){uploadAnexos.refresh()},5000);}