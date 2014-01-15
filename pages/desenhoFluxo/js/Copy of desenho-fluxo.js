
var TAMANHO_CONECTOR = 10;
var TOLERANCIA_SELECAO = 5;
var TAMANHO_LINHA_HORIZONTAL = 12; 

var ACIMA = 0;
var DIREITA = 1;
var ABAIXO = 2;
var ESQUERDA = 3;

var TEXT = "text";
var TEXT_AREA = "textarea";
var SELECT = "select";

var idElemento = 0;
var desenhoFluxo = null;
var selecao = null;

function pressionou_TECLA_DEL(shift, ctrl, alt, node){
	if (desenhoFluxo != null && ctrl) {
		desenhoFluxo.excluiElemento();
	}
}

function clone(obj,copy) { 
    if (null == obj || "object" != typeof obj) return obj; 
    for (var attr in obj) { 
    	if (obj.hasOwnProperty(attr)) 
        	copy[attr] = obj[attr]; 
    }  
}

function doubleParaStr(double) {
	var str = new String(double);
	return str.replace('.',',');	
}

function strParaDouble(str) {
	var str = new String(str);
	return parseFloat(str.replace(',','.'));	
}

function exibePropriedades() {
	if (selecao == null || selecao.elemento == null)
		return;
	
	document.formPropriedades.idElemento.value = selecao.elemento.idElemento;
	var bExistePropriedade = false;
	var strTable = "<table class='tabela'>";
	strTable += "<tr>";
	strTable += "   <th  class='th'>Propriedade</th>";
	strTable += "   <th  class='th'>Valor</th>";
	strTable += "</tr>";
	if (selecao.isSequencia()) {
		var sequencia = selecao.elemento;
		strTable += "<tr>";
		strTable += "   <td class='td'>Condição</td>";
		strTable += "   <td class='td'><textarea style='font-family:Arial;font-size:10px' name='condicao' id='condicao' rows='2' "+
				 "cols='75' onblur='desenhoFluxo.defineValorPropriedade("+sequencia.idElemento+",\"condicao\")' ></textarea></td>";
		strTable += "</tr>";
		bExistePropriedade = true;
	}else{
		var elementoFluxo = selecao.elemento;
		if (elementoFluxo.propriedades != null && elementoFluxo.propriedades != undefined) {
			for ( var i = 0; i < elementoFluxo.propriedades.length; i++) {
				var propriedade = elementoFluxo.propriedades[i];
				strTable += "<tr>";
				strTable += "   <td  class='td'>"+propriedade.nome+"</td>";
				strTable += "   <td class='td'>"+montaEntrada(elementoFluxo, propriedade)+"</td>";
				strTable += "</tr>";
				bExistePropriedade = true;
			}
		}
	}
	strTable += "</table>";
	if (bExistePropriedade)
		document.getElementById("divPropriedades").innerHTML = strTable;
	else
		document.getElementById("divPropriedades").innerHTML = "";
	HTMLUtils.setValuesForm(document.formPropriedades, selecao.elemento);
}

function montaEntrada(elementoFluxo, propriedade) {
	var result = "";
	
	if (propriedade.tipo == TEXT) {
		result = "<input style='font-family:Arial;font-size:10px' type='text' name='"+propriedade.id+"' id='"+propriedade.id+"' maxlength='"+ propriedade.tamanhoMaximo +
				 "' size='"+ propriedade.tamanho + "' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' />";
	}else if (propriedade.tipo == TEXT_AREA) {
		result = "<textarea style='font-family:Arial;font-size:10px' name='"+propriedade.id+"' id='"+propriedade.id+
				 "' rows='2' cols='"+ propriedade.tamanho + "' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' ></textarea>";
	}else if (propriedade.tipo == SELECT) {
		result = "<select style='font-family:Arial;font-size:10px' name='"+propriedade.id+"' id='"+propriedade.id + "' onclick='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' >";
		result += "<option value=''>--- Selecione ---</option>";
		for ( var i = 0; i < propriedade.opcoes.length; i++) {
			var opcao = propriedade.opcoes[i];
			result += "<option value='"+opcao.id + "'>"+opcao.texto + "</option>";
		}
		result += "</select>";
	}
	return result;
}

function DesenhoFluxo(_canvas){
	this.canvas = _canvas;
	this.tiposDeElemento = [];
	this.contexto = this.canvas.getContext("2d");
	
	this.elementos = [];
	this.elementos.length = 0;
	
	this.sequencias = [];
	this.sequencias.length = 0;
	
	var self = this;
	
	var interval = null;
	
	this.setTiposDeElemento = function(_tiposDeElemento) {
		this.tiposDeElemento = _tiposDeElemento;
	}
	
	this.resetaLista = function(){
		this.elementos = [];
		this.elementos.length = 0;
		
		this.sequencias = [];
		this.sequencias.length = 0;
		idElemento = 0;
	};
	
	this.configuraEventos = function(){
		//eventos para prevenir bugs
		this.canvas.addEventListener("dragenter", prevent, true);
		this.canvas.addEventListener("dragover", prevent, true);
		this.canvas.addEventListener("dragexit", prevent, true);
		//quando um item for solto dentro do canvas
		this.canvas.addEventListener("drop", droped, true);

		this.canvas.addEventListener("mousedown", mouseDown, true);
		this.canvas.addEventListener("mouseup", mouseUp, true);
		//se o mouse sair do canvas executo a aÃ§Ã£o de mouse up e a aÃ§Ã£o de arrastar termina
		this.canvas.addEventListener("mouseout", mouseOut, true);
		this.canvas.addEventListener("mousemove", mouseMove, true);
	}

	var mouseMove = function(e){
		self.canvas.style.cursor = 'default';	
		var sel = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
		if(sel != null) {
			if (sel.isBorda()){
				if (sel.isSequencia())
					self.canvas.style.cursor = 'move';	
				else	
					self.canvas.style.cursor = 'se-resize';	
			} else if (sel.isConexao()) {	
				self.canvas.style.cursor = 'crosshair';
			}else{
				self.canvas.style.cursor = 'pointer';
			}
		}
	}
	
	var mouseDown = function(e) {
		self.canvas.addEventListener("mousemove", mouseClickedMove, false);
		selecao = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
		if (selecao != null) {
			self.canvas.style.cursor = 'progress';
			exibePropriedades();
		}
	};
	
	this.defineValorPropriedade = function(idElemento, idPropriedade) {
		var elemento = buscaElementoPorId(idElemento);
		if (elemento == null) 
			return;
		
		if (document.formPropriedades.idElemento.value != idElemento)
			return;
		
		var elem = document.formPropriedades.elements[idPropriedade];
		if (elem.name == null) 
			return;

		elemento.defineValorPropriedade(idPropriedade, HTMLUtils.getValue(elem.id));
		atualizarCanvas();
	};
	
	var posicaoMouse;
	var mouseClickedMove = function(e) {
		self.canvas.style.cursor = 'move';	
		var posicaoMouse = getPosicaoRelativaCursor(e);
		if(selecao != null) {
			if (selecao.isSequencia()) {
				if (selecao.isBorda()) {
					selecao.elemento.borda.x = posicaoMouse.x;
					selecao.elemento.borda.y = posicaoMouse.y;
					selecao.elemento.posicaoAlterada = "S";
					atualizarCanvas();			
				}
			} else if (selecao.isConexao()){	
				self.canvas.style.cursor = 'crosshair';	
				atualizarCanvas();			

				self.contexto.beginPath(); 
				self.contexto.strokeStyle = "black";	
				self.contexto.lineWidth = 1;
				self.contexto.moveTo(selecao.conexao.getCentroX(), selecao.conexao.getCentroY());
				self.contexto.lineTo(posicaoMouse.x, posicaoMouse.y);
				self.contexto.closePath();
				self.contexto.stroke();
				
				var selecaoDestino = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
				if (selecaoDestino != null && selecaoDestino.isConexao()) {
					selecaoDestino.elemento.destaca();
				}
				
			} else if (selecao.isBorda()){
				self.canvas.style.cursor = 'se-resize';	
				atualizarCanvas();			
				if (posicaoMouse.x > selecao.elemento.posX) {
					var borda = selecao.borda;
					var variacaoX = posicaoMouse.x - (selecao.elemento.posX + selecao.elemento.largura);
					var variacaoY = posicaoMouse.y - (selecao.elemento.posY + selecao.elemento.altura);
					if (selecao.elemento.largura + variacaoX < selecao.elemento.larguraPadrao ||
						selecao.elemento.altura + variacaoY < selecao.elemento.alturaPadrao ||
						posicaoMouse.x < selecao.elemento.posX ||
						posicaoMouse.y < selecao.elemento.posY) {
						borda.x = selecao.elemento.posX + selecao.elemento.larguraPadrao;
						borda.y = selecao.elemento.posY + selecao.elemento.alturaPadrao;
					}else{ 
						borda.x = posicaoMouse.x;
						borda.y = posicaoMouse.y;
					}
					self.contexto.fillStyle = "rgba(255, 232, 165, 0.4)";
					self.contexto.strokeStyle = "#eee";
					self.contexto.lineWidth = 1;
					self.contexto.beginPath();
					self.contexto.fillRect(selecao.elemento.posX-TOLERANCIA_SELECAO, selecao.elemento.posY-TOLERANCIA_SELECAO, 
										 (borda.x - selecao.elemento.posX),	(borda.y - selecao.elemento.posY));
					self.contexto.strokeRect(selecao.elemento.posX-TOLERANCIA_SELECAO, selecao.elemento.posY-TOLERANCIA_SELECAO, 
							 			 (borda.x - selecao.elemento.posX),	(borda.y - selecao.elemento.posY));
					self.contexto.closePath();
					self.contexto.fill();
					self.contexto.stroke();   
				}
			} else {		
				determinaPosicaoElemento(posicaoMouse, selecao.elemento);
				atualizarCanvas();
			}		
		}		
	};
	
	var mouseUp = function(e) {
		self.canvas.style.cursor = 'default';	
		self.canvas.removeEventListener("mousemove", mouseClickedMove, false);	
		if(selecao != null){
			if (!selecao.isSequencia()) {
				var elemento = selecao.elemento;
				if (selecao.isBorda()){
					var borda = selecao.borda;
					var variacao = (borda.x - (elemento.posX + elemento.largura));
					var perc = (elemento.largura + variacao) / elemento.largura;
					elemento.largura = parseInt(elemento.largura * perc);
					elemento.altura = parseInt(elemento.altura * perc);
					if (elemento.largura < elemento.larguraPadrao) 
						elemento.largura = elemento.larguraPadrao;
					if (elemento.altura < elemento.alturaPadrao) 
						elemento.altura = elemento.alturaPadrao;
				}else if (selecao.isConexao()) {
					var selecaoOrigem = selecao;
					var selecaoDestino = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
					if (selecaoDestino != null && selecaoDestino.isConexao() && !existeConexao(elemento, selecaoDestino.elemento)) {
						var sequencia = new SequenciaFluxo(self.contexto); 
						sequencia.inicializa(selecaoOrigem, selecaoDestino); 
						self.sequencias.push(sequencia);
						selecaoDestino.elemento.desenhaConexoes();
					}
				}
			}
			atualizarCanvas();
		}
	};
	
	var existeConexao = function(elementoOrigem, elementoDestino) {
		var bExisteConexao = elementoOrigem.idElemento == elementoDestino.idElemento;
		if (!bExisteConexao) {
			for ( var i = 0; i < self.sequencias.length; i++) {
				var sequenciaFluxo = self.sequencias[i];
				if (sequenciaFluxo == null)
					continue;
				if (sequenciaFluxo.idElementoOrigem == elementoOrigem.idElemento && sequenciaFluxo.idElementoDestino == elementoDestino.idElemento) {
					bExisteConexao = true;
					break;
				}
			}
		}
		return bExisteConexao;
	}
	
	var mouseOut = function(e) {
		self.canvas.style.cursor = 'default';	
		self.canvas.removeEventListener("mousemove", mouseClickedMove, false);	
	};

	var prevent = function(e) {
		e.preventDefault();
		if (e.stopPropagation) {
			// this code is for Mozilla and Opera
			e.stopPropagation();
		} else if (window.e) {
			// this code is for IE
			window.e.cancelBubble = true;
		}
		return false;
	};

	var droped = function(e) {
		prevent(e);

		var idTipoElemento = parseInt(e.dataTransfer.getData("text"));
		var tipo = self.tiposDeElemento[idTipoElemento];
		if(tipo == null){
			UtilMapa.mostrarMsgTemporaria("msg_erro", 4, "Item inválido!");
			return;
		}
		var elemento = new ElementoFluxo(self.contexto);
		clone(tipo, elemento);
		elemento.posiciona(getPosicaoRelativaCursor(e));
		self.elementos.push(elemento);
		selecao = new Selecao(elemento, null, null);
		atualizarCanvas();
		exibePropriedades();
	};	
	
	this.limpa = function() {
		self.contexto.save();
		self.contexto.clearRect(0, 0, self.canvas.width, self.canvas.height);
		self.contexto.restore();
	}
	
	this.atualiza = function() {
		self.limpa();
		for ( var i = 0; i < self.elementos.length; i++) {
			var elemento = self.elementos[i];
			
			if(elemento == null)
				continue;
			elemento.desenha();
		}

		for (s = 0; s < self.sequencias.length; s++) {
			var seq = self.sequencias[s];
			if (seq == null)
				continue;
			try{
				seq.desenha();
			}catch (e) {
				alert('s: '+s+'  origem: '+seq.idElementoOrigem);
			}
		}
	}
	
	var atualizarCanvas = function() {
		self.atualiza();
	}
		
	var buscaElementoPorId = function(id) {
		for (var i = 0; i < self.elementos.length; i++) {
			if (self.elementos[i] != null) {
				if (self.elementos[i].idElemento == id) {
					return self.elementos[i];
				}
			}
		}
		for (i = 0; i < self.sequencias.length; i++) {
			if (self.sequencias[i] != null) {
				if (self.sequencias[i].idElemento == id) {
					return self.sequencias[i];
				}
			}
		}
	};
	
	var buscaElementoNaPosicao = function(posicao){	
		var retorno = null;
		for(var i = 0; i < self.elementos.length; i++){
			var elemento = self.elementos[i];
			if(elemento != null){
				for(var p = 0; p < elemento.conexoes.length; p++){
					var conexao = elemento.conexoes[p];
					if((posicao.x >= conexao.x - TOLERANCIA_SELECAO) && 
					   (posicao.x <= conexao.x + conexao.largura + TOLERANCIA_SELECAO) &&
					   (posicao.y >= conexao.y - TOLERANCIA_SELECAO) && 
					   (posicao.y <= conexao.y + conexao.altura + TOLERANCIA_SELECAO)){
						retorno = new Selecao(elemento, conexao, null);
						break;
					}
				}
				if (retorno == null) {
					for(p = 0; p < elemento.bordas.length; p++){
						var borda = elemento.bordas[p];
						if((posicao.x >= borda.x) && 
						   (posicao.x <= borda.x + TOLERANCIA_SELECAO) &&
						   (posicao.y >= borda.y) && 
						   (posicao.y <= borda.y + TOLERANCIA_SELECAO)){
							retorno = new Selecao(elemento, null, borda);
							break;
						}
					}
				}
				if (retorno == null) {
					if(posicao.x >= elemento.posX && posicao.x <= elemento.getPosX2() && 
					   posicao.y >= elemento.posY && posicao.y <= elemento.getPosY2()){	
						retorno = new Selecao(elemento, null, null);
						break;
					}
				}
			}
		}
		if (retorno == null) {
			for(i = 0; i < self.sequencias.length; i++){
				var sequencia = self.sequencias[i];
				if (sequencia == null || sequencia.borda == null)
					continue;
				if((posicao.x >= sequencia.borda.x - TOLERANCIA_SELECAO) && 
				   (posicao.x <= sequencia.borda.x + TOLERANCIA_SELECAO) &&
				   (posicao.y >= sequencia.borda.y - TOLERANCIA_SELECAO) && 
				   (posicao.y <= sequencia.borda.y + TOLERANCIA_SELECAO)){
					retorno = new Selecao(sequencia, null, sequencia.borda);
					break;
				}else if(posicao.x >= sequencia.posX && posicao.x <= sequencia.getPosX2() && 
						 posicao.y >= sequencia.posY && posicao.y <= sequencia.getPosY2()){
							var cor = self.contexto.getImageData(posicao.x, posicao.y, 1, 1);
							if (cor.data[0] == 0 && cor.data[1] == 0 && cor.data[2] == 0) {
								retorno = new Selecao(sequencia, null, null);
								break;
							}
				}
			}
		}
		return retorno;
	};

	var getPosicaoRelativaCursor = function(evt) {
		var obj = self.canvas;
		var top = 0;
		var left = 0;
		while (obj && obj.tagName != 'BODY') {
			top += obj.offsetTop;
			left += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		var mouseX = evt.clientX - left + window.pageXOffset;
		var mouseY = evt.clientY - top + window.pageYOffset;
		return {
			x : mouseX,
			y : mouseY
		};
	};
	
	var determinaPosicaoElemento = function(posicaoMouse, elemento) {
		var limiteTempX = (self.canvas.width - elemento.largura);
		var limiteTempY = (self.canvas.height - elemento.altura);		
		elemento.posX = parseInt(posicaoMouse.x <= limiteTempX ? (posicaoMouse.x - elemento.largura/2) : limiteTempX);
		elemento.posY = parseInt(posicaoMouse.y <= limiteTempY ? (posicaoMouse.y - elemento.altura/2): limiteTempY);	
	}
	
	this.excluiElemento = function() {
		if (selecao != null) {
			if (!selecao.isSequencia()) {
				var i = 0;
				while (i < self.elementos.length){
					var elemento = self.elementos[i];
					if (elemento == null)
						return;
					if (elemento.isSelecionado()) {
						self.elementos.splice(i,1);
						var s = 0;
						while (s < self.sequencias.length){
							var sequencia = self.sequencias[s];
							if (sequencia == null)
								return;
							if (sequencia.idElementoOrigem == elemento.idElemento || sequencia.idElementoDestino == elemento.idElemento) {
								self.sequencias.splice(s,1);
							}else{
								s ++;
							}
						}
					}else{
						i++;
					}
				}
			}else{
				var s = 0;
				while (s < self.sequencias.length){
					var sequencia = self.sequencias[s];
					if (sequencia == null)
						return;
					if (sequencia.isSelecionado()) {
						self.sequencias.splice(s,1);
					}else{
						s ++;
					}
				}
			}
			atualizarCanvas();
		}
	}
	
	
	this.renderizaElementos = function(elementos_serializados, sequencias_serializadas) {
		self.resetaLista();
		self.limpa();
		
		var elementos_fluxo = JSON.parse(elementos_serializados);
		for(var i = 0; i < elementos_fluxo.length; i++){
			var elementoFluxo = elementos_fluxo[i];
			var elemento = new ElementoFluxo(self.contexto);
			clone(elementoFluxo, elemento);
			elemento.posiciona(new Coordenada(elemento.posX, elemento.posY));
			elemento.desenha();
			self.elementos.push(elemento);
			if (elemento.idElemento > idElemento)
				idElemento = elemento.idElemento;
		}
		var sequencias_fluxo = JSON.parse(sequencias_serializadas);
		for(i = 0; i < sequencias_fluxo.length; i++){
			var sequenciaFluxo = sequencias_fluxo[i];
			var seq = new SequenciaFluxo(self.contexto);
			clone(sequenciaFluxo, seq);
			seq.elementoOrigem = buscaElementoPorId(seq.idElementoOrigem);
			seq.elementoDestino = buscaElementoPorId(seq.idElementoDestino);
			seq.localizaConexoes();
			if (seq.bordaX > 0 && seq.bordaY > 0)
				seq.borda = new Coordenada(seq.bordaX, seq.bordaY);
			seq.desenha();
			self.sequencias.push(seq);
		}
	}	
	
	this.grava = function() {
		var elem = [];
		var seq = []
		for(var i = 0; i < self.elementos.length; i++){
			var elemento = self.elementos[i];
			if (elemento == null)
				continue;
			elem.push(elemento);
		}
		for(i = 0; i < self.sequencias.length; i++){
			var sequencia = self.sequencias[i];
			if (sequencia == null)
				continue;
			
			if (sequencia.borda != null) {
				sequencia.bordaX = sequencia.borda.x;
				sequencia.bordaY = sequencia.borda.y;
			}else{
				sequencia.bordaX = -1;
				sequencia.bordaY = -1;
			}
			sequencia.bordaX = doubleParaStr(sequencia.bordaX);
			sequencia.bordaY = doubleParaStr(sequencia.bordaY);
			seq.push(sequencia);
		}
		
		document.form.elementos_serializados.value = ObjectUtils.serializeObjects(elem);
		document.form.sequencias_serializadas.value = ObjectUtils.serializeObjects(seq);
		document.form.save();
		
		for(i = 0; i < self.sequencias.length; i++){
			var sequencia = self.sequencias[i];
			if (sequencia == null)
				continue;
			sequencia.bordaX = strParaDouble(sequencia.bordaX);
			sequencia.bordaY = strParaDouble(sequencia.bordaY);
		}		
	}
};

function Coordenada(x, y) {
	this.x = x;
	this.y = y;
}

function Selecao(elemento, conexao, borda) {
	this.elemento = elemento;
	this.conexao = conexao;
	this.borda = borda;
	
	this.isConexao = function() {
		return !this.elemento.isSequencia() && this.conexao != null;
	}
	
	this.isBorda = function() {
		return this.borda != null;
	}

	this.isSequencia = function() {
		return this.elemento.isSequencia();
	}
	
	this.getElementoSerializado = function() {
		return JSON.stringify(this.elemento);
	}
}

function SequenciaFluxo(_contexto) {
	idElemento ++;
	this.idElemento = idElemento;
	this.contexto = _contexto;
	this.conexaoOrigem;
	this.conexaoDestino;
	
	this.posicaoAlterada;
	
	this.idElementoOrigem;
	this.idElementoDestino;

	this.idConexaoOrigem;
	this.idConexaoDestino;
	this.bordaX;
	this.bordaY;
	
	this.condicao = '';
	
	this.largura;
	this.altura;
	this.posX;
	this.posY;
	
	this.elementoOrigem;
	this.elementoDestino;
	
	this.borda = null;
	
	this.inicializa = function(_selecaoOrigem, _selecaoDestino) {
		this.elementoOrigem = _selecaoOrigem.elemento;
		this.elementoDestino = _selecaoDestino.elemento;
		this.conexaoOrigem = _selecaoOrigem.conexao;
		this.conexaoDestino = _selecaoDestino.conexao;
		this.idElementoOrigem = _selecaoOrigem.elemento.idElemento;
		this.idElementoDestino = _selecaoDestino.elemento.idElemento;
		this.idConexaoOrigem = this.conexaoOrigem.posicao;
		this.idConexaoDestino = this.conexaoDestino.posicao;
		this.bordaX = -1;
		this.bordaY = -1;
		this.posicaoAlterada = "N";
	}

	this.getPosX2 = function(){
		return this.posX + this.largura;
	};
	
	this.getPosY2 = function(){
		return this.posY + this.altura;
	};	
	
	this.isSequencia = function() {
		return true;
	}
	
	this.isSelecionado = function() {
		return selecao != null && selecao.isSequencia() &&
					  selecao.elemento.idElementoOrigem == this.idElementoOrigem && selecao.elemento.idElementoDestino == this.idElementoDestino;
	}
	
	this.destaca = function() {
		this.contexto.lineWidth = 1;
		this.contexto.beginPath();
		this.contexto.strokeStyle = "#eee";
		this.contexto.strokeRect(this.posX, this.posY, this.largura, this.altura);
		this.contexto.closePath();
		this.contexto.stroke();
	}	
	
	this.localizaConexoes = function() {
		if (this.elementoOrigem  != null && this.idConexaoOrigem != null) 
			this.conexaoOrigem = this.elementoOrigem.conexoes[this.idConexaoOrigem];
		if (this.elementoDestino  != null && this.idConexaoDestino != null)
			this.conexaoDestino = this.elementoDestino.conexoes[this.idConexaoDestino];
	}
	
	this.desenha = function() {		
		var centroXOrigem = this.conexaoOrigem.getCentroX();
		var centroYOrigem = this.conexaoOrigem.getCentroY();
		
		var centroXDestino = this.conexaoDestino.getCentroX();
		var centroYDestino = this.conexaoDestino.getCentroY();
		
		if (centroYDestino == centroYOrigem && centroXDestino == centroXOrigem)
			return;
				
		var posOrigemX;
		var posOrigemY;
		var posDestinoX;
		var posDestinoY;

		switch (this.conexaoOrigem.posicao) {
			case ACIMA:
				posOrigemX = centroXOrigem;
				posOrigemY = centroYOrigem - TAMANHO_LINHA_HORIZONTAL;
				break;
			case DIREITA:
				posOrigemX = centroXOrigem + TAMANHO_LINHA_HORIZONTAL;
				posOrigemY = centroYOrigem;
				break;
			case ABAIXO:
				posOrigemX = centroXOrigem;
				posOrigemY = centroYOrigem + TAMANHO_LINHA_HORIZONTAL;
				break;
			case ESQUERDA:
				posOrigemX = centroXOrigem - TAMANHO_LINHA_HORIZONTAL;
				posOrigemY = centroYOrigem;
				break;
		}

		switch (this.conexaoDestino.posicao) {
			case ACIMA:
				posDestinoX = centroXDestino;
				posDestinoY = centroYDestino - TAMANHO_LINHA_HORIZONTAL;
				break;
			case DIREITA:
				posDestinoX = centroXDestino + TAMANHO_LINHA_HORIZONTAL;
				posDestinoY = centroYDestino;
				break;
			case ABAIXO:
				posDestinoX = centroXDestino;
				posDestinoY = centroYDestino + TAMANHO_LINHA_HORIZONTAL;
				break;
			case ESQUERDA:
				posDestinoX = centroXDestino - TAMANHO_LINHA_HORIZONTAL;
				posDestinoY = centroYDestino;
				break;
		}

		if (posOrigemX > posDestinoX) {
			this.largura = posOrigemX - posDestinoX;
			this.posX = posDestinoX; 
		}else{
			this.largura = posDestinoX - posOrigemX;
			this.posX = posOrigemX; 
		}
			
		if (posOrigemY > posDestinoY) {
			this.altura = posOrigemY - posDestinoY;
			this.posY = posDestinoY;
		}else{
			this.altura = posDestinoY - posOrigemY;
			this.posY = posOrigemY;
		}		
		
		if (this.posicaoAlterada == undefined || this.posicaoAlterada == 'N') 
			this.borda = new Coordenada(this.posX+this.largura/2, this.posY+this.altura/2);
		
		this.contexto.beginPath();
		this.contexto.strokeStyle = "black";
		this.contexto.fillStyle = "black";
		if (this.isSelecionado()) {
			this.contexto.lineWidth = 3;
		}else{
			this.contexto.lineWidth = 1;
		}

		this.contexto.moveTo(posOrigemX,posOrigemY);
		this.contexto.lineTo(centroXOrigem,centroYOrigem);
		
		this.contexto.moveTo(posDestinoX,posDestinoY);
		this.contexto.lineTo(centroXDestino,centroYDestino);
		
		
		this.contexto.moveTo(posOrigemX,posOrigemY);
		this.contexto.lineTo(this.borda.x, this.borda.y);
		this.contexto.moveTo(this.borda.x, this.borda.y);
		this.contexto.lineTo(posDestinoX, posDestinoY);

		this.contexto.closePath();	
		this.contexto.stroke();
		
		this.desenhaSeta(this.conexaoDestino);

		this.contexto.beginPath();
		this.contexto.strokeStyle = "rgba(64, 64, 64, 0.3)";
		this.contexto.lineWidth = 1;
		this.contexto.moveTo(this.borda.x, this.borda.y - 1.5*TOLERANCIA_SELECAO);
		this.contexto.lineTo(this.borda.x, this.borda.y + 1.5*TOLERANCIA_SELECAO);
		this.contexto.moveTo(this.borda.x-1.5*TOLERANCIA_SELECAO, this.borda.y);
		this.contexto.lineTo(this.borda.x+1.5*TOLERANCIA_SELECAO, this.borda.y);		
		this.contexto.closePath();	
		this.contexto.stroke();
		//this.destaca();
		
		//alert('Origem: '+this.conexaoOrigem.posicao+'  Destino: '+this.conexaoDestino.posicao);
	}	
	
	this.desenhaSeta = function(conexao) {
		this.contexto.beginPath();
		switch (conexao.posicao) {
			case ACIMA:
			this.contexto.moveTo(conexao.x, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y);
			break;
		case DIREITA:
			this.contexto.moveTo(conexao.x+conexao.largura, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y+conexao.altura);
			break;
		case ABAIXO:
			this.contexto.moveTo(conexao.x, conexao.y+conexao.altura);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y+conexao.altura);
			break;
		case ESQUERDA:
			this.contexto.moveTo(conexao.x, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x, conexao.y+conexao.altura);
			break;
		}
		this.contexto.closePath();	
		this.contexto.stroke();
		this.contexto.fill();
	}		
	
	this.defineValorPropriedade = function(id, valor) {
		this[id] = valor;
	}

}

function Conexao(x, y, l, a, posicao) {
	this.x = x;
	this.y = y;
	this.largura = l;
	this.altura = a;
	this.posicao = posicao;
	
	this.getCentroX = function() {
		return this.x + (this.largura / 2);
	}
	this.getCentroY = function() {
		return this.y + (this.altura / 2);	
	}
}

function ElementoFluxo(_context) {
	idElemento ++;
	
	this.idElemento = idElemento;
	this.propriedades;
	
	this.posX;
    this.posY;

	this.largura = 0;
	this.altura = 0;

	this.imagem;
	
	this.contexto = _context;
    this.imagemObj;
	
	this.conexoes = [];
	this.conexoes.length = 0;
	
	this.bordas = [];
	this.bordas.length = 0;
	
	this.larguraPadrao;
	this.alturaPadrao;
	
	this.borda;
	this.ajustavel;
	
	var self = this;	

	this.getPosX2 = function(){
		return this.posX + this.largura;
	};
	
	this.getPosY2 = function(){
		return this.posY + this.altura;
	};
	
	this.getXCentral = function(){
		return this.posX + (this.largura/2);
	};
	
	this.getYCentral = function(){
		return this.posY + (this.altura/2);
	};
	
	this.isSelecionado = function() {
		return selecao != null && !selecao.isSequencia() && selecao.elemento != null && selecao.elemento.idElemento == this.idElemento;
	}

	this.posiciona = function(_posicao) {
		this.imagemObj = new Image();
		if (this.largura == 0)
			this.largura = this.larguraPadrao;
		if (this.altura == 0)
			this.altura = this.alturaPadrao;
		this.posX = parseInt(_posicao.x);
		this.posY = parseInt(_posicao.y);
		this.imagemObj.src = "imagens/" + this.imagem;
	};
	
	this.desenha = function() {
		if (this.imagemObj == null)
			return;
		if (this.borda)
			this.contexto.drawImage(this.imagemObj, this.posX + 3, this.posY + 3, this.imagemObj.width, this.imagemObj.height);
		else
			this.contexto.drawImage(this.imagemObj, this.posX, this.posY, this.imagemObj.width, this.imagemObj.height);
		this.desenhaConexoes();
		this.exibeNome();
	};
	
	this.destaca = function() {
		this.contexto.beginPath();
		if (this.isSelecionado() && !this.borda) {
			this.contexto.strokeStyle = "black";
			this.contexto.lineWidth = 3;
		}else{
			this.contexto.strokeStyle = "#eee";
			this.contexto.lineWidth = 1;
		}
		this.contexto.strokeRect(this.posX - TOLERANCIA_SELECAO, this.posY - TOLERANCIA_SELECAO, this.largura + 2*TOLERANCIA_SELECAO, this.altura + 2*TOLERANCIA_SELECAO);
		this.contexto.closePath();
		this.contexto.stroke();
		
		if (this.conexoes.lenght == 0)
			return;
		
		var c1 = this.conexoes[0]; 
		var c2 = this.conexoes[1]; 
		var c3 = this.conexoes[2]; 
		var c4 = this.conexoes[3]; 
		
		this.contexto.beginPath();
		this.contexto.fillStyle = "rgba(64, 64, 64, 0.3)";
		//this.contexto.fillStyle = "rgba(255, 166, 94, 0.3)";
		this.contexto.fillRect(c1.x, c1.y, c1.largura, c1.altura);
		this.contexto.fillRect(c2.x, c2.y, c2.largura, c2.altura);
		this.contexto.fillRect(c3.x, c3.y, c3.largura, c3.altura);
		this.contexto.fillRect(c4.x, c4.y, c4.largura, c4.altura);
		this.contexto.closePath();
		this.contexto.fill();
		this.contexto.stroke();		
	}
	
	this.desenhaConexoes = function(){
		var c1;
		var c2;
		var c3;
		var c4;
		if (this.conexoes.length == 0) {
			c1 = new Conexao((this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2), (this.posY   - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ACIMA);
			c2 = new Conexao((this.posX + this.largura - TAMANHO_CONECTOR / 2), (this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, DIREITA);
			c3 = new Conexao((this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2), (this.posY + this.altura  - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ABAIXO);
			c4 = new Conexao((this.posX  - TAMANHO_CONECTOR / 2), (this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ESQUERDA);
			this.conexoes[0] = c1;
			this.conexoes[1] = c2;
			this.conexoes[2] = c3;
			this.conexoes[3] = c4;
		}else{
			c1 = this.conexoes[0]; 
			c2 = this.conexoes[1]; 
			c3 = this.conexoes[2]; 
			c4 = this.conexoes[3]; 
			
			c1.x = this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2;
			c1.y = this.posY - TAMANHO_CONECTOR / 2;

			c2.x = this.posX + this.largura - TAMANHO_CONECTOR / 2;
			c2.y = this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2;

			c3.x = this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2;
			c3.y = this.posY + this.altura  - TAMANHO_CONECTOR / 2;
			
			c4.x = this.posX  - TAMANHO_CONECTOR / 2;
			c4.y = this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2;
			
		}
	
		this.bordas = [];
		//this.bordas.push(new Coordenada(this.posX, this.posY));
		//this.bordas.push(new Coordenada(this.posX + this.largura, this.posY));
		//this.bordas.push(new Coordenada(this.posX, this.posY + this.altura));
		
		
		if (this.isSelecionado()) {
			this.destaca();
		}
	
		if (this.ajustavel) {
			var borda = new Coordenada(this.posX + this.largura + TOLERANCIA_SELECAO, this.posY + this.altura + TOLERANCIA_SELECAO);
			this.bordas.push(borda);
			
			this.contexto.beginPath();
			this.contexto.strokeStyle = "rgba(64, 64, 64, 0.3)";
			this.contexto.lineWidth = 1;
			this.contexto.moveTo(borda.x, borda.y - TOLERANCIA_SELECAO);
			this.contexto.lineTo(borda.x, borda.y + TOLERANCIA_SELECAO);
			this.contexto.moveTo(borda.x-TOLERANCIA_SELECAO, borda.y);
			this.contexto.lineTo(borda.x+TOLERANCIA_SELECAO, borda.y);		
			this.contexto.closePath();	
			this.contexto.stroke();
		}
		
		if (this.borda) {
			this.contexto.beginPath();
			if (this.isSelecionado()) {
				this.contexto.lineWidth = 3;
			}else{
				this.contexto.lineWidth = 1;
			}
			this.contexto.strokeStyle = "rgba(0, 0, 0, 1)";
			this.contexto.lineJoin = "round";
			this.contexto.fillStyle = "rgba(255, 255, 154, 0.3)";
			this.contexto.fillRect(this.posX, this.posY, this.largura, this.altura);
			this.contexto.strokeRect(this.posX, this.posY, this.largura, this.altura);
			this.contexto.closePath();
			this.contexto.stroke();
			this.contexto.fill();
		}

	};
	
	this.isSequencia = function() {
		return false;
	}
	
	this.defineValorPropriedade = function(id, valor) {
		this[id] = valor;
		if (id == 'nome')
			this.nome = valor;
	}
	
	this.exibeNome = function() {
		if (this.nome == '' || this.nome == undefined)
			return;
		this.contexto.beginPath();		
		this.contexto.fillStyle = "rgba(0, 0, 0, 1)";
		this.contexto.font = "8pt Arial";
		this.contexto.fillText(this.nome, this.posX+20, this.getYCentral()+5, this.largura-25);			
		this.contexto.closePath();		
	}
	
};


