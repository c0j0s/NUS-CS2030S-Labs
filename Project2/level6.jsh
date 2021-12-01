UnaryOperator<Integer> op = x -> { System.out.printf("iterate: %d -> %d\n", x, x + 2); return x + 2; };
Function<Integer,Integer> doubler = x -> { System.out.printf("map: %d -> %d\n", x, x * 2); return x * 2; };
Function<Integer,Integer> half = x -> { System.out.printf("map: %d -> %d\n", x, x / 2); return x / 2; };
Function<Integer,Integer> power2 = x -> { System.out.printf("map: %d -> %d\n", x, x * x); return x * x; };
Predicate<Integer> moreThan72 = x -> { System.out.printf("takeWhile: %d -> %b\n", x, x < 71); return x < 71; };
Consumer<Integer> foreach = x -> { if(x % 6 == 0) System.out.printf("forEach: %d\n", x * x);};
InfiniteList.iterate(36, op).map(doubler).map(half).filter(moreThan72).limit(19).forEach(foreach)
1211	iterate: 36 -> 38	 	
1212	map: 38 -> 76	 	
1213	map: 76 -> 38	 	
1214	filter: 38 -> false	

1215	iterate: 38 -> 40	 	
1216	map: 40 -> 80	 	
1217	map: 80 -> 40	 	
1218	filter: 40 -> false	 	
1219	iterate: 40 -> 42	 	
1220	map: 42 -> 84	 	
1221	map: 84 -> 42	 	
1222	filter: 42 -> true	 	
1223	takeWhile: 42 -> true	 	
1224	map: 42 -> 1764	 	
1225	forEach: 1764
