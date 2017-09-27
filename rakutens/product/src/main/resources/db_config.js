use productsdb;
db.createUser({
	user: "app",
	pwd: "rakuten",
	roles:["readWrite"]
});

db.product.createIndex({description:1});
db.product.createIndex({categoryCode:1, description:1});

let id = 0;
function addProduct(desc, eurPrice, categoryCode){
	db.product.insert({_id: "" + (++id), description: desc, eurPrice : eurPrice, categoryCode: categoryCode});
}


db.product.remove({});
addProduct("RCA - Amplified digital indoor", 16.99, "elc-aud-ant");
addProduct("Ultra Motorized HDTV Outdoor Antenna LAVA HD-2605", 69.95, "elc-aud-ant");
addProduct("Esky HG-981 HDTV 1080p Outdoor", 36.99, "elc-aud-ant");
addProduct("HDTV Motorized Remote Outdoor Amplified Antenna 360° UHF/VHF/FM HD TV 150 Miles", 33.94, "elc-aud-ant");
addProduct("Rosewill RHTA-15004 UHF/VHF", 14.99, "elc-aud-ant");
addProduct("GoPro HERO+ LCD HD Waterproof", 149.95, "elc-photo"); 
addProduct("Nikon D3300 Digital Camera + 18-55mm", 559.95, "elc-photo");
addProduct("Nikon D500 DX-format DSLR Body", 1896.95, "elc-photo");
addProduct("LifeBox UltraCharge 10000 White", 54.99, "elc-car"); 
addProduct("Pioneer AVH-4200NEX Car DVD", 469.99, "elc-car");
addProduct("Chevy Car 2000-2005 Radio AM FM CD w Upgraded Aux 3.5mm iPod Input RDS 10335223", 185, "elc-car");
addProduct("Apple MacBook Air MQD32LL/A 13.3''", 891.89, "elc-comp");
addProduct("Apple MacBook Pro 15.4'' Retina Display", 899, "elc-comp");
addProduct("Apple MacBook Pro MD101LL/A 13.3''", 1020.29, "elc-comp");
addProduct("Microsoft Surface Pro 4 Tablet - 12.3''", 979, "elc-comp");
addProduct("ZTE TREK 2 HD K88 AT&T Unlocked", 73.99, "elc-comp");
