  function internacionalizar(parametro){
	  
	/*  if(parametro == 'en'){
		  
		  document.getElementById('linguagemAtiva').src = '../../novoLayout/common/theme/images/lang/en.png';
		
	  }else{
		  document.getElementById('linguagemAtiva').src = '../../novoLayout/common/theme/images/lang/br.png'

	  }*/
	  
	  
		document.getElementById('locale').value = parametro;
		document.formInternacionaliza.fireEvent('internacionaliza');
	}