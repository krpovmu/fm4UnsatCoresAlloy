open util/ordering[State] as ord
abstract sig Object { eats: set Object }
one sig Farmer, Fox, Chicken, Grain extends Object {}
fact eating {
	eats = Fox->Chicken + Chicken->Grain 
}
sig State { near,far: set Object }
fact initialState {
	let s0 = ord/first | s0.near = Object && no s0.far 
}
pred crossRiver[from,from',to,to': set Object] {
	(from' = from - Farmer && to' = to - to.eats + Farmer )
	|| (some item: from - Farmer {
	from' = from - Farmer - item && to' = to - to.eats + Farmer + item }) }
fact stateTransition {
	all s: State, s': ord/next[s] {
	Farmer in s.near => crossRiver[s.near, s'.near, s.far, s'.far]
	else crossRiver[s.far, s'.far, s.near, s'.near] }
}
pred solvePuzzle { ord/last.far = Object }
pred test1 {
	some disj F0: Farmer | some disj X0: Fox |
	some disj C0: Chicken | some disj G0: Grain |
	some disj F0, X0, C0, G0: Object |
	some disj S0, S1, S2, S3: State {
		Farmer = F0
		Fox = X0
		Chicken = C0
		Grain = G0
		Object = F0 + X0 + C0 + G0
		eats = X0->C0 + C0->G0
		State = S0 + S1 + S2 + S3
		near = S0->F0 + S0->X0 + S0->C0 + S0->G0 + S1->X0 + S2->F0 + S2->X0 + S3->X0
		far = S1->F0 + S1->G0 + S2->G0 + S3->F0 + S3->G0
		ord/first = S0
		ord/next = S0->S1 + S1->S2 + S2->S3
		crossRiver[F0 + X0 + C0 + G0, C0, none, F0 + X0] 
	} 
}
run test1 for 4 expect 1
