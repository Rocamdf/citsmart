/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $("validarUsuario:user").focus();
});
//oncomplete="handleComplete(xhr, status, args)"
function handleComplete(xhr, status, args) {     
    //alert(args.detail);
    //alerta.show();
    if(args.validationFailed || !args.loggedIn) {  
        alerta.show();
    }    
} 

