La passphrase di tutte le chiavi private e': mypay

openssl req -config openssl.cnf -x509 -nodes -days 900 -newkey rsa:4096 -keyout star.mypay.key -out star.mypay.crt
