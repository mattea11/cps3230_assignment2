package aspects;

import main.EndPoints;
import main.MarketUMAuto;

import larva.*;
public aspect _asp_MarketUM0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_MarketUM0.initialize();
}
}
before () : (call(* *.goToLogInInvalid(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 40/*badLogAttempt*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 40/*badLogAttempt*/);
}
}
before () : (call(* *.goToAlerts(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 42/*alerts*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 42/*alerts*/);
}
}
before () : (call(* *.goToHome(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 44/*home*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 44/*home*/);
}
}
before () : (call(* *.accessMarketUm(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 36/*notLogged*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 36/*notLogged*/);
}
}
before () : (call(* *.goToLogInValid(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 38/*goodLogAttempt*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 38/*goodLogAttempt*/);
}
}
before () : (call(* *.goToLogOut(..)) && args(*) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_MarketUM0.lock){

_cls_MarketUM0 _cls_inst = _cls_MarketUM0._get_cls_MarketUM0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 46/*logOut*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 46/*logOut*/);
}
}
}