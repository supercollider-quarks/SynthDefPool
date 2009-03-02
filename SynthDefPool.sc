/*
SynthDefPool - a quark to facilitate sharing SynthDefs in a structured way
Created by Dan Stowell 2009
*/
SynthDefPool {

	classvar <global;
	var <poolpath, <dict;


	*new { |poolpath|
	  ^super.newCopyArgs(poolpath).init;
	}
	init {
	  dict = IdentityDictionary.new;
	  (poolpath ++ "/*.scd").pathMatch.do{ |apath|
	  	// Lazy loading - ignore the file contents for now
	  	dict.put(apath.basename.splitext[0].asSymbol, 0); // we put "0" because can't actually put nil into a dictionary...
	  };
	}

	*initClass {
	  StartUp.add{
	    global = this.new(this.filenameSymbol.asString.dirname +/+ "pool")
	  }
	}

	*at { |key|
		^global.at(key)
	}
	at { |key|
	  // Lazy loading
	  if(dict[key]==0){
	    dict[key] = thisProcess.interpreter.compileFile("%/%.scd".format(poolpath, key)).value;
	  };
	  ^dict[key]
	}
	scanAll { // Unlazy
		dict.keysDo{|key| this[key]}
	}
	forget { // relazy
		dict.keysDo{|key| this[key] = 0}
	}

	*gui {
		^global.gui
	}
	gui {
		var w, list, listview, wrect;
		Server.default.waitForBoot{
			this.memStore;
			
			w = Window("<SynthDefPool>", Rect(0, 0, 600, 50).center_(Window.screenBounds.center));
			list = dict.keys(Array);
			list.sort;
			
			wrect = w.view.bounds;
			listview = PopUpMenu(w, wrect.copy.width_(wrect.width/2).insetBy(5, 15)).items_(list);
			
			Button(w, wrect.copy.left_(wrect.width*3/6).width_(wrect.width/6).insetBy(5, 15))
				.states_([["makeWindow"]])
				.action_{ SynthDescLib.at(listview.item).makeWindow };
			Button(w, wrect.copy.left_(wrect.width*4/6).width_(wrect.width/6).insetBy(5, 15))
				.states_([["usage"]])
				.action_{ Document.new(string:"s.boot;
SynthDefPool.at(\\"++listview.item++").memStore; // ensure the server knows about it
x = Synth(\\"++listview.item++", [\\freq, 440]);
x.set(\\freq, 330);
"
				).syntaxColorize.promptToSave_(false) };
			Button(w, wrect.copy.left_(wrect.width*5/6).width_(wrect.width/6).insetBy(5, 15))
				.states_([["source"]])
				.action_{ Document.open(poolpath +/+ listview.item ++ ".scd") };
			
			w.front;
		};

	}

	// Conveniences to load/store/etc all our defs
	*writeDefFile { |dir|
		^global.writeDefFile(dir)
	}
	writeDefFile { |dir|
		this.scanAll;
		dict.do{|def| def.writeDefFile(dir)};
	}
	*load { |server|
		^global.load(server)
	}
	load { |server|
		this.scanAll;
		dict.do{|def| def.load(server)};
	}
	*store { |libname=\global, completionMsg, keepDef = true, mdPlugin| 
		^global.store(libname, completionMsg, keepDef, mdPlugin)
	}
	store { |libname=\global, completionMsg, keepDef = true, mdPlugin|
		this.scanAll;
		dict.do{|def| def.memStore(libname, completionMsg, keepDef, mdPlugin)};
	}
	*memStore { |libname=\global, completionMsg, keepDef = true| 
		^global.memStore(libname, completionMsg, keepDef)
	}
	memStore { |libname=\global, completionMsg, keepDef = true|
		this.scanAll;
		dict.do{|def| def.memStore(libname, completionMsg, keepDef)};
	}


} // end class
