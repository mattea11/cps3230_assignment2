package larva;


import main.EndPoints;
import main.MarketUMAuto;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_MarketUM0 implements _callable{

public static PrintWriter pw; 
public static _cls_MarketUM0 root;

public static LinkedHashMap<_cls_MarketUM0,_cls_MarketUM0> _cls_MarketUM0_instances = new LinkedHashMap<_cls_MarketUM0,_cls_MarketUM0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\matte\\Desktop\\university\\3rd year\\CPS3230 - Fundamentals of Software Testing\\assignment2/src/output_MarketUM.txt");

root = new _cls_MarketUM0();
_cls_MarketUM0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_MarketUM0 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int state =-1 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_MarketUM0() {
}

public void initialisation() {
}

public static _cls_MarketUM0 _get_cls_MarketUM0_inst() { synchronized(_cls_MarketUM0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_MarketUM0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_MarketUM0_instances){
_performLogic_badLoginsProperty(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_MarketUM0[] a = new _cls_MarketUM0[1];
synchronized(_cls_MarketUM0_instances){
a = _cls_MarketUM0_instances.keySet().toArray(a);}
for (_cls_MarketUM0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_MarketUM0_instances){
_cls_MarketUM0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_badLoginsProperty = 9;

public void _performLogic_badLoginsProperty(String _info, int... _event) {

_cls_MarketUM0.pw.println("[badLoginsProperty]AUTOMATON::> badLoginsProperty("+") STATE::>"+ _string_badLoginsProperty(_state_id_badLoginsProperty, 0));
_cls_MarketUM0.pw.flush();

if (0==1){}
else if (_state_id_badLoginsProperty==7){
		if (1==0){}
		else if ((_occurredEvent(_event,42/*alerts*/))){
		;
_cls_MarketUM0.pw .println ("User went to alerts page successfully!");

		_state_id_badLoginsProperty = 7;//moving to state alerts
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,44/*home*/))){
		;
_cls_MarketUM0.pw .println ("User went to home page successfully!");

		_state_id_badLoginsProperty = 6;//moving to state home
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,46/*logOut*/))){
		;
_cls_MarketUM0.pw .println ("User logged out successfully!");

		_state_id_badLoginsProperty = 9;//moving to state unLogged
		_goto_badLoginsProperty(_info);
		}
}
else if (_state_id_badLoginsProperty==8){
		if (1==0){}
		else if ((_occurredEvent(_event,42/*alerts*/))){
		;
_cls_MarketUM0.pw .println ("Went to alerts page!");

		_state_id_badLoginsProperty = 7;//moving to state alerts
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,44/*home*/))){
		;
_cls_MarketUM0.pw .println ("Went to home page!");

		_state_id_badLoginsProperty = 6;//moving to state home
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,46/*logOut*/))){
		;
_cls_MarketUM0.pw .println ("User logged out successfully!");

		_state_id_badLoginsProperty = 9;//moving to state unLogged
		_goto_badLoginsProperty(_info);
		}
}
else if (_state_id_badLoginsProperty==9){
		if (1==0){}
		else if ((_occurredEvent(_event,40/*badLogAttempt*/))){
		;
_cls_MarketUM0.pw .println ("user has failed to log!");

		_state_id_badLoginsProperty = 9;//moving to state unLogged
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,38/*goodLogAttempt*/))){
		;
_cls_MarketUM0.pw .println ("User has logged in!");

		_state_id_badLoginsProperty = 8;//moving to state logged
		_goto_badLoginsProperty(_info);
		}
}
else if (_state_id_badLoginsProperty==6){
		if (1==0){}
		else if ((_occurredEvent(_event,44/*home*/))){
		;
_cls_MarketUM0.pw .println ("User went to home page successfully!");

		_state_id_badLoginsProperty = 6;//moving to state home
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,42/*alerts*/))){
		;
_cls_MarketUM0.pw .println ("User went to alerts page successfully!");

		_state_id_badLoginsProperty = 7;//moving to state alerts
		_goto_badLoginsProperty(_info);
		}
		else if ((_occurredEvent(_event,46/*logOut*/))){
		;
_cls_MarketUM0.pw .println ("User logged out successfully!");

		_state_id_badLoginsProperty = 9;//moving to state unLogged
		_goto_badLoginsProperty(_info);
		}
}
}

public void _goto_badLoginsProperty(String _info){
_cls_MarketUM0.pw.println("[badLoginsProperty]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_badLoginsProperty(_state_id_badLoginsProperty, 1));
_cls_MarketUM0.pw.flush();
}

public String _string_badLoginsProperty(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "alerts"; else return "alerts";
case 8: if (_mode == 0) return "logged"; else return "logged";
case 9: if (_mode == 0) return "unLogged"; else return "unLogged";
case 6: if (_mode == 0) return "home"; else return "home";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}