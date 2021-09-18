void serveCruises(Cruise[] listOfCruise) {
    ArrayList<Loader> loaders = new ArrayList<Loader>();

    for(Cruise c: listOfCruise) {           
        int loaderNeeded = c.getNumOfLoadersRequired();
              
        //check for available loaders
        for(int i = 0; i < loaders.size(); i++) {
            //try to get a new loader
            Loader newLoader = loaders.get(i).serve(c);
            
            //update the main loader list if existing loader is serving
            if (!loaders.get(i).equals(newLoader) && loaderNeeded > 0){
                //update loader master list
                loaders.set(i, newLoader);

                //print loaders assigned for current cruise
                System.out.println(newLoader);

                //offset number of loaders needed
                loaderNeeded--;
            }
        }

        //create new loader if none are available
        if (loaderNeeded > 0) {
            int size = loaders.size() + 1;
            for(int i = 0; i < loaderNeeded; i++) {            
                int id = size + i;
                Loader newLoader = new Loader(id, c);

                //print loaders assigned for current cruise
                System.out.println(newLoader);

                //update loader master list
                loaders.add(newLoader);
            }
        }

    }

}
