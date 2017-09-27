use categoriesdb;
db.createUser({
	user: "app",
	pwd: "rakuten",
	roles:["readWrite"]
});

db.category.createIndex({ancestors:1});


function addCateg(id, desc, ancestors){
	db.category.insert({_id: id, description: desc, ancestors: ancestors});
}

db.category.remove({});
addCateg("elc", "Electronics", []);
addCateg("elc-audio", "Audio & Video Components", ["elc", "all"]);
addCateg("elc-photo", "Camera & Photo", ["elc", "all"]);
addCateg("elc-car", "Car Audio & Video", ["elc", "all"]);
addCateg("elc-comp", "Computers", ["elc", "all"]);
addCateg("elc-home", "Home Audio", ["elc", "all"]);
addCateg("hlt", "Health", []);
addCateg("hlt-alt", "Alternative Medicine", ["hlt"]);
addCateg("hlt-alt-ac", "Acupuncture", ["hlt-alt", "hlt"]);
addCateg("hlt-ear", "Ear Care", ["hlt"]);
addCateg("hlt-ear-d", "Ear Drops", ["hlt-ear", "hlt"]);
