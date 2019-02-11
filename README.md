# CS_278_Lab3
NMSU class by H. leung Lab3. 

(
	(
		All, 
		(
			P(x) -> 
			(
				Q(x) \/ R(x)
			)
		)
	)

	^ 
	(
		Exists
		(
			Q(x) ^ R(x)
		)
	)
)
->
(
	Exists
	(
		P(x) ^ R(x)
	)
)
----------------------------------------------
W = f1() -> f2()
	f1() = f3() ^ f4()
		f3() = All(x) P(x) -> f6(x)
			f6(x) = Q(x) \/ R(x)
		f4() = Exists(x) f7(x)
			f7(x) = Q(x) ^ R(x)

	f2() = Exists(x) f5(x)
		f5(x) = P(x) ^ R(x)
-------------------------------------------
W = f1() -> f2()
f1() = f3() ^ f4()
f2() = Exists(x) f5(x)
f3() = All(x) P(x) -> f6(x)
f4() = Exists(x) f7(x)
f5(x) = P(x) ^ R(x)
f6(x) = Q(x) \/ R(x)
f7(x) = Q(x) ^ R(x)

W2 = (( forAll(x), ( P(x) -> (Q(x) \/ R(x)))) ^ ( Exists(x), ( Q(x) ^ R(x)))) -> (Exists(x), (P(x) ^ R(x)))

p = 1, q = 1, r = 0 ( model ? )
f7 = false
f6 = true
f5 = false
f4 = false
f3 = true
f2 = false
f1 = false
w = true
Is model

p = 0, q = 1, r = 1 ( countermodel ? )
f7 = true
f6 = true
f5 = false
f4 = true
f3 = true
f2 = false
f1 = true
w = false
Is not model
