function njen(){return NjsRuntime.enterFrame.apply(NjsRuntime,arguments);}
function njex(frame,retval){return NjsRuntime.exitFrame(frame,retval);}
function njkeys(obj){return NjsRuntime.keys(obj);}
function NjsRuntime(){}
NjsRuntime.CALL=1;NjsRuntime.RETURN=2;NjsRuntime.createFrame=function(parentFrame){var frame=function(retval){NjsRuntime.returnToFrame(arguments.callee,retval);}
for(var n in frame){delete(frame[n]);}
frame.isNJFrame=true;frame.parentFrame=parentFrame;frame.cp=0;return frame;}
NjsRuntime.enterFrame=function(frameThis,args){var index=args.length-1;var frame=args[index];if(frame==null||typeof(frame)!="function"||!frame.isNJFrame){frame=this.createFrame(null);}else{if(frame.state==this.CALL){frame=this.createFrame(frame);}else if(frame.state==this.RETURN){}else{throw new Error("Invalid frame state: "+frame.state);}
delete args[index];args.length=index;}
if(frame.frameThis==null){frame.frameThis=frameThis;frame._arguments=args;for(var i=2;i<arguments.length;i++){frame['_'+arguments[i]]=args[i-2];}}
frame.state=this.CALL;return frame;}
NjsRuntime.exitFrame=function(frame,retval){if(frame.parentFrame)
this.returnToFrame(frame.parentFrame,retval);}
NjsRuntime.returnToFrame=function(frame,retval){frame.state=this.RETURN;frame["rv"+frame.cp]=retval;frame._arguments.callee.call(frame.frameThis,frame);}
NjsRuntime.keys=function(obj){var keys=[];for(var n in obj){keys.push(n);}
return keys;}
function Queue(){this.queue=[];}
Queue.prototype.push_tail=function(msg){this.queue.reverse();this.queue.push(msg);this.queue.reverse();};Queue.prototype.push_head=function(msg){this.queue.push(msg);};Queue.prototype.pop=function(){return this.queue.pop();};Queue.prototype.is_empty=function(){return this.queue.length==0;};var current_process;var process_queue=new Queue();var pid_count=0;function Process(thunk){var p=this;p.pid=++pid_count;p.cont=function(){current_process=p;thunk();current_process=undefined;};p.mailbox=new Queue();p.blocked=false;}
function send(process,msg){var njf0=njen(this,arguments,"process","msg");nj:while(1){switch(njf0.cp){case 0:if(njf0._process.blocked){njf0._process.blocked=false;schedule(njf0._process);}
njf0._process.mailbox.push_tail(njf0._msg);njf0.cp=1;concede(njf0);return;case 1:break nj;}}njex(njf0);}
function block_if_no_messages(k){if(current_process.mailbox.is_empty()){var p=current_process;p.blocked=true;p.cont=function(){current_process=p;k();current_process=undefined;};next_process();}}
function receive(){var njf0=njen(this,arguments);nj:while(1){switch(njf0.cp){case 0:njf0.cp=1;block_if_no_messages(njf0);return;case 1:njf0._msg=current_process.mailbox.pop();njex(njf0,njf0._msg);return;break nj;}}njex(njf0);}
function schedule(process){process_queue.push_tail(process);start_scheduler();}
function unschedule(){return process_queue.pop();}
function concede(k){var p=current_process;p.cont=function(){current_process=p;k();current_process=undefined;};schedule(p);next_process();}
function sleep(ms,cont){var njf0=njen(this,arguments,"ms","cont");nj:while(1){switch(njf0.cp){case 0:njf0._s=10;case 1:njf0.cp=(njf0._s<njf0._ms)?2:3;break;case 2:njf0.cp=4;concede(njf0);return;case 4:njf0._s+=10;njf0.cp=1;break;case 3:break nj;}}njex(njf0);}
function start(processes){for(var i=0;i<processes.length;++i){process_queue.push_head(processes[i]);}
start_scheduler();}
function spawn(func){var p=new Process(func);start([p]);return p;}
var scheduler_started=false;function start_scheduler(){if(!scheduler_started){scheduler_started=true;while(!process_queue.is_empty()){next_process();}}
scheduler_started=false;}
function next_process(){var p=unschedule();if(p){var k=p.cont;if(k){setTimeout(k,10);}}}
var objetos;function ObjectToProcess(array){var obj=this;obj.arrayScriptExecute=array;obj.execute=function(){for(var j=0;j<array.length;j++){eval(array[j]);}};}