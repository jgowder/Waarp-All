Configuration Avancée
#####################

EN plus des arguments détaillés ci-dessous, il est possible d'ajouter
d'autres paramètres aux commandes Java pour adapter le comportement,
voir :ref:`ici <raw-commands>`.


client.xml
**********

============================ ==================== ==============
Balise                       Type (Default)       Description
============================ ==================== ==============
comment                      String

**identity**
hostid                       String               Identifiant pour connection non SSL
sslhostid                    String               Identifiant pour connection SSL
cryptokey                    Des-File             Fichier de la clef DES pour le chiffrement des mots de passe
authentfile                  XML-File             Fichier d'authentification aux partenaires

**ssl** (Optional)
keypath                      JKS-File             JKS KeyStore pour les accès R66 via SSL (Contient le certificat serveur)
keystorepass                 String               Mot de passe du JSK <keypath>
keypass                      String               Mot de passe du Certificat du jks <keypath>
trustkeypath                 JKS-File             JKS TrustStore pour les accès R66 via SSL en utilisant l'authentification client (contient les certificats client)
trustkeystorepass            String               Mot de passe du JSK <trustkeypath>
trustuseclientauthenticate   Boolean              Si vrai, R66 n'acceptera que les clients authorisés via SSL

**directory**
serverhome                   Directory            Dossier racine du moniteur R66 (Les chemins sont calculés depuis ce dossier)
in                           String (IN)          Dossier par défaut de reception
out                          String (OUT)         Dossier par défaut d'envoie
arch                         String (ARCH)        Dossier par défaut d'archive
work                         String (WORK)        Dossier par défaut de travail
conf                         String (CONF)        Dossier par défaut de configuration

**db**
dbdriver                     String               Driver JDBC à utiliser pour se connecter à la base de données (mysql, postgresql, h2)
dbserver                     String               URI JDBC de connection à la base de données (jdbc:type://[host:port]....). Veuillez vous référer à la documentation de votre base de donnée pour la syntaxe correcte
dbuser                       String               Utilisateur à utiliser pour se connecter à la base de données
dbpasswd                     String               Mot de passe de l’utilisateur

**extendTaskFactory**
extendedtaskfactories        String (vide)        Liste (séparée par des virgules) des TaskFactory en tant qu'extension pour ajouter des tâches à WaarpR66
============================ ==================== ==============

server.xml
**********

.. tabularcolumns:: |\Y{0.3}|\Y{0.2}|\Y{0.5}|

================================ ==================== ==============
Balise                           Type (Default)       Description
================================ ==================== ==============
comment                          String

**identity**
hostid                           String               Identifiant pour connection non SSL
sslhostid                        String               Identifiant pour connection SSL
cryptokey                        Des-File             Fichier de la clef DES pour le chiffrement des mots de passe
authentfile                      XML-File             Fichier d'authentification aux partenaires

**server**
serveradmin                      String               Login pour l'accès administrateur
serverpasswd                     String               Mot de passe pour l'accès administrateur chiffré par <cryptokey>
serverpasswdfile                 String               Fichier PGP pour l'accès administrateur chiffré par <cryptokey> (Choisir <serverpasswd> ou <serverpasswdfile>)
usenossl                         Boolean (True)       Autorise les connections non SSL
usessl                           Boolean (False)      Autorise les connections SSL (voir configuration SSL plus bas)
usehttpcomp                      Boolean (False)      Authorise la compression HTTP pour l'interface d'administration (HTTPS)
uselocalexec                     Boolean (False)      Si vrai utilise R66 LocalExec Daemon au lieu de System.exec()
lexecaddr                        Address (127.0.0.1)  Addresse du Daemon LocalExec
lexecport                        Integer (9999)       Port du Daemon LocalExec
httpadmin                        Directory            Dossier racine de l'interface d'administration (HTTPS)
admkeypath                       JKS-File             JKS KeyStore pour les accès administrateur en HTTPS (Contient les certificats serveur)
admkeystorepass                  String               Mot de passe du <admkeypath> KeyStore
admkeypass                       String               Mot de passe du certificat serveur dans <admkeypath>
checkaddress                     Boolean (False)      R66 vérifiera l'addresse IP distance pendant l'acceptation de la connection
checkclientaddress               Boolean (False)      R66 vérifiera l'addresse IP distance du client distant
multiplemonitors                 Integer (1)          Défini le nombre de serveur du même groupe considéré comme une unique instance R66
businessfactorynetwork           String               Nom de classe complet de la Business Factory. (org.waarp.openr66.context.R66DefaultBusinessFactory)

**network**
serverport                       Integer (6666)       Port utilisé pour les connections en clair
serveraddresses                  String  (null)       Adresses utilisées pour le protocole R66 (séparées par des virgules)
serversslport                    Integer (6667)       Port utilisé pour les connections chiffrées
serverssladdresses               String  (null)       Adresses utilisées pour le protocole R66 en SSL (séparées par des virgules)
serverhttpport                   Integer (8066)       Port de l'interface HTTP de monitoring, 0 désactive cette interface
serverhttpaddresses              String  (null)       Adresses utilisées pour l'interface web de supervision (séparées par des virgules)
serverhttpsport                  Integer (8067)       Port de l'interface HTTPS d'administration, 0 désactive cette interface
serverhttpsaddresses             String  (null)       Adresses utilisées pour l'interface web HTTPS d'administration (séparées par des virgules)
serverrestport                   Integer (-1)         Port de l'API REST HTTP(S), -1 désactive cette interface


**ssl** (Optional)
keypath                          JKS-File             JKS KeyStore pour les accès R66 via SSL (Contient le certificat serveur)
keystorepass                     String               Mot de passe du JSK <keypath>
keypass                          String               Mot de passe du Certificat du jks <keypath>
trustkeypath                     JKS-File             JKS TrustStore pour les accès R66 via SSL en utilisant l'authentification client (contient les certificats client)
trustkeystorepass                String               Mot de passe du JSK <trustkeypath>
trustuseclientauthenticate       Boolean              Si vrai, R66 n'acceptera que les clients authorisés via SSL

**directory**
serverhome                       Directory            Dossier racine du moniteur R66 (Les chemins sont calculés depuis ce dossier)
in                               String (IN)          Dossier par défaut de reception
out                              String (OUT)         Dossier par défaut d'envoie
arch                             String (ARCH)        Dossier par défaut d'archive
work                             String (WORK)        Dossier par défaut de travail
conf                             String (CONF)        Dossier par défaut de configuration

**limit**
serverthread                     Integer (n*2 + 1)    Nombre de threads serveur (n=Nombre de coeur)
clientthread                     Integer (10*n)       Nombre de threads client
memorylimit                      Integer (1000000000) Limite mémoire des services HTTP et REST
sessionlimit                     Integer (1GB)        Limitation de bande passante par session (1GB)
globallimit                      Integer (100GB)      Limitation de bande passante globale (100GB)
delaylimit                       Integer (10000)      Interval entre 2 vérification de bande passante
runlimit                         Integer (1000)       Limite du nombre de transfers actifs (maximum 50000)
delaycommand                     Integer (5000)       Interval entre 2 execution du Commander (5s)
delayretry                       Integer (30000)      Interval avant une nouvelle tentative de transfert en cas d'erreur (30s)
timeoutcon                       Integer (30000)      Interval avant l'envoie d'un Time Out (30s)
blocksize                        Integer (65536)      Taille des blocs (64Ko). Une valeur entre 8 ko et 16 Mo est recommandé
gaprestart                       Integer (30)         Nombre de blocs doublonnés en cas d'arrêt puis reprise d'un transfert
usenio                           Boolean (False)      Support NIO des fichiers. Paramètre obsolète
usecpulimit                      Boolean (False)      Limitation du CPU via la gestion de la bande passante
usejdkcpulimit                   Boolean (False)      Limitation CPU basé sur le JDSK natif, sinon Java Sysmon library est utilisé
cpulimit                         Decimal (0.0)        % de CPU, 1.0 ne produit aucune limite
connlimit                        Integer (0)          Limitation du nombre de connection
digest                           Integer (2)          Utilisation d'un Digest autre que MD5 (7 pour SHA-512 recommandé)
usefastmd5                       Boolean (False)      Utilisation de la bibliothèque FastMD5 (paramètre obsolète)
fastmd5                          SODLL                Path vers la JNI. Si vide, la version core de Java sera utilisée
checkversion                     Boolean (True)       Utilisation du protocole etendu (>= 2.3), accès à plus de retour d'information en fin de transfert
globaldigest                     Boolean (True)       Utilisation d'un digest global (MD5, SHA1, ...) par transfert de fichier
localdigest                      Boolean (True)       Utilisation d'un digest local (MD5, SHA1, ...) en fin de transfert (optionnel)
compression                      Boolean (False)      Active ou Désactive la compression à la volée par bloc, puis en fonction du partenaire (nécessite le mot clef #COMPRESS# dans chaque règle où l'on veut le voir actif)

**db**
dbdriver                         address              Driver JDBC à utiliser pour se connecter à la base de données (mysql, postgresql, h2)
dbserver                         String               URI JDBC de connection à la base de données (jdbc:type://[host:port]....). Veuillez vous référer à la documentation de votre base de donnée pour la syntaxe correcte
dbuser                           String               Utilisateur de la base de données
dbpasswd                         String               Mot de passe de la base de données
dbcheck                          Boolean (True)       Vérification de la base de données au démarage
taskrunnernodb                   Boolean (False)      WaarpR66 serveur sans base, utilise les fichiers comme information permanente sur les tâches de transfert

**rest**
restssl                          Boolean (False)      Utilisation de SSL par l'interface REST
restdelete                       Boolean (False)      Authorisation de DELETE par l'interface REST
restauthenticated                Boolean (False)      Utilisation de l'authentification par l'interface REST
resttimelimit                    Long (-1)            Time out de l'interface REST
restauthkey                      Path                 Clef d'authentification SHA 256 de l'interface REST

**business**
businessid                       String               L'hostid (1 by 1) authorisé à utiliser des Business Request

**roles**
role                             Array                Remplace le rôle de l'ĥôte en base de données
roleid                           String               L'hostid (1 à 1) concerné par le remplacement
roleset                          StringArray          Les nouveaux rôle attribués

**aliases**
alias                            Array                Permets d'utiliser des alias au lieu des hostid
realid                           String               Hostid aliassé (l'alias est local)
aliasid                          StringArray          L'ensemble des alias de l'hostid

**extendTaskFactory**
extendedtaskfactories            String (vide)        Liste (séparée par des virgules) des TaskFactory en tant qu'extension pour ajouter des tâches à WaarpR66

**pushMonitor**
*Partie commune*
url                              String (null)        URL de base pour les exports du moniteur en mode POST HTTP(S) JSON
delay                            Integer (1000)       Délai entre deux vérifications de changement de statuts sur les transferts
intervalincluded                 Boolean (True)       Si « True », les informations de l'intervalle utilisé seront fournies
transformlongasstring            Boolean (False)      Si « True », les nombres « long » seront convertis en chaîne de caractères, sinon ils seront numériques
token                            String (null)        Spécifie si nécessaire le token  dans le cadre d'une authentification via Token
apiKey                           String (null)        Spécifie si nécessaire le password dans le cadre d'une authentification via ApiKey (format ``apiId:apiKey``)
*Partie API REST*
endpoint                         String (null)        End point à ajouter à l'URL de base
keepconnection                   Boolean (True)       Si « True », la connexion HTTP(S) sera en Keep-Alive (pas de réouverture sauf si le serveur la ferme), sinon la connexion sera réinitialisée pour chaque appel
basicAuthent                     String (null)        Spécifie si nécessaire l'authentification basique
*Partie Elasticsearch*
index                            String (null)        Contient le nom de l'index avec de possibles substitutions, dont ``%%WARPHOST%%`` pour le nom du host concerné, et les ``%%DATETIME%%``, ``%%DATEHOUR%%``, ``%%DATE%%``, ``%%YEARMONTH%%``, ``%%YEAR%%`` pour des substitutions de date et heure partiellement (``yyyy.MM.dd.HH.mm`` à ``yyyy``)
prefix                           String (null)        Spécifie si nécessaire un prefix global dans le cas d'usage d'un Proxy devant Elasticsearch
username                         String (null)        Spécifie si nécessaire le username (et son password) dans le cadre d'une authentification basique
paswd                            String (null)        Spécifie si nécessaire le password dans le cadre d'une authentification basique
compression                      Boolean (True)       Spécifie si les flux sont compressés (par défaut True)
================================ ==================== ==============



Les balises <roles> et <aliases> contiennent des listes d'option. Exemple:

.. code-block:: xml

  ...
  <roles>
    <role>
      <roleid>DummyHost1</roleid>
      <roleset>RoleA</roleset>
    </role>
    <role>
      <roleid>DummyHost2</roleid>
      <roleset>RoleA RoleC</roleset>
    </role>
    <role>
      <roleid>DummyHost3</roleid>
      <roleset>RoleC RoleD RoleE</roleset>
    </role>
  </roles>
  <aliases>
    <alias>
      <realid>DummyHost1</realid>
      <aliasid>AliasC</aliasid>
    </alias>
    <alias>
      <realid>DummyHost4</realid>
      <aliasid>AliasA AliasB</aliasid>
    </alias>
  </aliases>
  ...

Optimisation
************

Il peut être nécessaire de paramétrer finement dans certains cas.

**Limitation de la mémoire**

Il est possible de limiter l'usage de la mémoire en usant des paramètres suivants :

*Limitation des services*

 * Services R66 : un des protocoles au moins doit être activé (TLS ou no TLS) ; si l'un des deux n'est pas
   utile, vous pouvez le désactiver (`usenossl` ou `usessl` à `False`)
 * `uselocalexec` : à `False` si aucun usage (exécution dans un processus externes des commandes EXECxxx)
   (valeur par défaut)
 * `serverhttpport` : si le monitoring HTTP est sans usage, vous pouvez le désactiver (`0`)
 * `serverhttpsport` : si le moteur d'administration HTTPS est sans usage, vous pouvez le désactiver (`0`)
   (non recomandé)
 * `serverrestport` : si le moteur REST est sans usage, vous pouvez le désactiver (`-1`, valeur par défaut)
 * `usethrift` : si le moteur THRIFT est sans usage,  vous pouvez le désactiver (`0`, valeur par défaut)
 * `pushMonitor` : si le Push Monitoring Exporter est sans usage, ne pas le déclarer

*Limitation des ressources*

 * `serverthread`: Possibilité de limiter le nombre de Threads dédiées à la partie serveur (y compris 1)
 * `clientthread`: Possibilité de limiter le nombre de Threads dédiées à la partie protocolaire (il est avisé
   de ne par mettre moins de 10)
 * `memorylimit`: Possibilité de limiter la taille mémoire maximale allouable pour décoder/encoder les pages
   HTTP et les réponses REST (minimum conseillé 100 Mo)
 * `runlimit`: Possibilité de limiter le nombre de transferts simultanés (il est avisé de ne pas mettre moins
   de 2)
 * `compression`: Possibilité de ne pas activer la compression à la volée (moins de mémoire et de cpu)

   * Possibilité de del déclarer mais sans utiliser le mot clef #COMPRESS# dans toutes les règles où l'on veut le voir actif)

 * de limiter l'impact processeur via une gestion adaptative de la bande passante globale :

   * `usecpulimit` à `True` : ceci active la fonctionnalité
   * `usejdkcpulimit` de préférence, laisser à `False` ou *ignoré* (permet de choisir l'implémentation
     sous-jacente analysant les ressources CPU)
   * `cpulimit` avec une valeur maximale autorisée pour la charge globale CPU, tous coeurs confondus (minimum
     conseillé `0.2`, en pratique `0.5` comme minimum) ; cette valeur détermine le seuil à partir duquel la
     bande passante globale sera progressivement diminuée afin de réduire l'activité CPU, puis remontée
   * `connlimit` en laissant à `0` ou *ignoré* (permet de limiter le nombre maximum de connexion mais
     souvent trop restrictif)

**Performances**

 * Usage de règles dans un mode sans empreinte par paquet de données (``SENDMODE`` = 1, ``RECVMODE`` = 2) au
   lieu des modes avec empreinte par paquet de données (``SENDMD5MODE`` = 3, ``RECVMD5MODE`` = 4) (environ 15%
   de gains)
 * `blocksize` : Possibilité d'augmenter la taille par défaut de 64KB à par exemple 256KB (en pratique,
   inutile d'aller au-delà), permettant de diminuer le nombre de paquets de données ainsi émis (uniquement
   valable sur de gros transferts)
 * `gaprestart` : Possibilité de diminuer la valeur par défaut (`30`) à `10`, permettant ainsi de
   restreindre la réémission des paquets à la reprise du transfert (au lieu de `30 x blocksize`, ce sera par
   exemple `10 x blocksize`)
 * `digest`: Possibilité de choisir des algorithmes plus performants (`CRC32`=0, `MD5`=2) ou avec moins de
   risques de collisions (`SHA-XXX` tel que `SHA-512`=7) (`SHA-512` est conseillé car très efficace)

   * `CRC32` est le plus performant (95% avec 6ms JDK11, 10ms JDK8) mais avec le plus de collisions,
   * `MD5` performant (55% avec 88ms JDK11, 105ms JDK8) mais avec encore des collisions
   * `SHA-512` est le plus performant des SHA (au moins 25% avec 70ms JDK11, 153ms JDK8) et aux collisions
     infimes
   * *chiffres comparés à `SHA-256` (159ms JDK11, 192ms JDK8)*

 * `globaldigest` : Possibilité de le désactiver mais recommandé à `True` (environ 25% de gains)
 * `localdigest` : Possibilité de le désactiver (`False`) (environ 20% de gains)
 * `runlimit` : Possibilité d'augmenter ou de diminuer la valeur par défaut (1000) entre 2 et 50000
   transferts concurrents
 * `compression`: Permet d'activer (désativée par défaut) la possibilité de compression à la volée des blocs
   et donc la vitesse des transferts sur des environnements à réseau contraint (nécessite le mot clef
   #COMPRESS# dans chaque règle où l'on veut le voir actif)


La performance d'autres éléments peuvent jouer :

 * La vitesse du processeur et de la mémoire

   * Il est conseillé de disposer d'au moins 2 coeurs et au moins 2 Go de mémoire disponible totalement
     pour Waarp, une valeur optimale étant 4 coeurs et 8 Go de mémoire

 * La vitesse du stockage sur lequel sont écrits les fichiers (limite naturelle du transfert)

   * Il est conseillé de disposer de disques très rapides (SSD ou FC). La vitesse en lecture (émission) ou
     en écriture (réception) peuvent en être impactées. Ceci concerne a minima le répertoire `WORK` et `IN`
     et dans une moindre mesure (lecture) `OUT`.

 * La vitesse et la latence du réseau sur lequel transite les données (limite naturelle du transfert)

*Mini-Benchmark*

Sur un Core I7 génération 5, 16 Go de mémoire, un disque rapide SSD de portable, un réseau local (`lo`),
en condition complète de vérification de cohérences (`digest` à `SHA-512` (7),
`globaldigest` et `localdigest` à `True`, et règle avec empreinte par paquet),
les transferts ont pu atteindre 65 MB/s (520 Mbits/s).

En réduisant les vérifications de cohérence (`digest` `globaldigest` maintenus mais `localdigest` à `False`
et règle sans empreinte par paquet), les performances sont montées à 80 MB/s (640 Mbits/s).

En supprimant toutes les vérifications de cohérence sauf celles des empreintes par paquet, le
débit atteint était de 110 MB/s (880 Mbits/s) (*ceci correspond au maximum du débit disque en écriture*).

Il est fortement déconseillé de désactiver totalement toutes les vérifications de cohérence, car il ne
pourra alors pas être assuré que le fichier transmis le sera sans défaut lors du transport (même si le
protocole s'appuie sur TCP/IP, il est possible d'avoir une corruption sur le réseau).

*Benchmarks Waarp R66*

Tous les benchmarks ont été réalisés sur un seul serveur à chaque fois, hébergeant tous les services
(2 moteurs Waarp R66 et 2 bases de données PostgreSQL/H2).
Seul le benchmark Cluster utilise une seule base PostgreSQL (commune) mais 1 client à part et de 1 à 4
serveurs Waarp R66 clusterisés.


**Evolution selon les versions**

L'évolution selon les versions depuis la 3.0 jusqu'à la dernière version.

============================== ======== === ============ ==== ==========================
Contexte                       Nb vCore TLS Transferts/s CPU  Gain
============================== ======== === ============ ==== ==========================
V3.0 Loop 2 Serveurs           4        Oui 30/s         100% Référence
V3.2 Loop 2 Serveurs           4        Oui 60/s         100% 100%
V3.5.2 Loop 2 Serveurs         4        Oui 71/s         100% 137%
V3.6.0 Loop 2 Serveurs         4        Oui 100/s        90%  233%
V3.6.1 Loop 2 Serveurs         4        Oui 124/s        80%  313%
============================== ======== === ============ ==== ==========================

**Vision globale des benchmarks V3.6.1**

================ ============== ============ ============ ========================
Modèle           TLS            NoTLS        Accélération Description
================ ============== ============ ============ ========================
Loop 2 coeurs    124/s          126/s        Référence    2 Serveurs en ping pong pour une taille moyenne de 250 Ko
Loop 2 coeurs    113/s          114/s        -4%          2 Serveurs en ping pong pour une taille moyenne de 250 Ko et Monitoring en mode PUSH REST
Loop 4 coeurs    150/s          149/s        21%          2 Serveurs en ping pong pour une taille moyenne de 250 Ko
Loop 4 coeurs    149/s          146/s        20%          2 Serveurs en ping pong pour une taille moyenne de 250 Ko et Monitoring en mode PUSH REST
Cluster 2 coeurs 73/s           75/s         Référence    Mode Cluster avec 1 seul serveur pour une taille moyenne de 250 Ko
Cluster 2 coeurs 116/s          116/s        21%          Mode Cluster avec 2 serveurs pour une taille moyenne de 250 Ko
Cluster 4 coeurs 85/s           86/s         17%          Mode Cluster avec 1 seul serveur pour une taille moyenne de 250 Ko
Cluster 4 coeurs 178/s          179/s        144%         Mode Cluster avec 2 serveurs pour une taille moyenne de 250 Ko
Cluster 4 coeurs 263/s          266/s        260%         Mode Cluster avec 3 serveurs pour une taille moyenne de 250 Ko
Cluster 4 coeurs 346/s          344/s        374%         Mode Cluster avec 4 serveurs pour une taille moyenne de 250 Ko
Gros Fichier 2c  152 MB/s       181 MB/s     Référence    Transfert d'un fichier de 500 Mo
Gros Fichier 4c  254 MB/s       301 MB/s     67%          Transfert d'un fichier de 500 Mo
================ ============== ============ ============ ========================

**Vision détaillée des benchmarks V3.6.1**

============================== ======== === ============ ==== =======================================
Contexte                       Nb vCore TLS Transferts/s CPU  Gain
============================== ======== === ============ ==== =======================================
V3.6.1 Loop 2 Serveurs         4        Oui 124/s        80%  Référence
V3.6.1 Loop 2 Serveurs Compres 4        Oui 123/s        75%  -1%
V3.6.1 Loop 2 Serveurs         8        Oui 150/s        35%  21%
V3.6.1 Loop 2 Serveurs Compres 8        Oui 150/s        35%  21%
V3.6.1 Loop 2 Serveurs         4        Non 126/s        80%  2%
V3.6.1 Loop 2 Serveurs Compres 4        Non 123/s        75%  -1%
V3.6.1 Loop 2 Serveurs         8        Non 149/s        35%  20%
V3.6.1 Loop 2 Serveurs Compres 8        Non 149/s        35%  20%
V3.6.1 Loop 2 Serveurs Monitor 4        Oui 113/s        85%  -9%
V3.6.1 Loop 2 Serveurs Monitor 8        Oui 149/s        40%  20%
V3.6.1 Loop 2 Serveurs Monitor 4        Non 114/s        80%  -8%
V3.6.1 Loop 2 Serveurs Monitor 8        Non 146/s        40%  18%
V3.6.1 Cluster 1 Serveurs      4        Oui 73/s         80%  -41% (Référence mono serveur TLS)
V3.6.1 Cluster 2 Serveurs      4        Oui 116/s        95%  -6% (+55% vs mono serveur TLS)
V3.6.1 Cluster 1 Serveurs      8        Oui 85/s         25%  14% (+17% vs mono serveur TLS)
V3.6.1 Cluster 2 Serveurs      8        Oui 178/s        45%  44% (+140% vs mono serveur TLS)
V3.6.1 Cluster 3 Serveurs      8        Oui 263/s        60%  112% (+260% vs mono serveur TLS)
V3.6.1 Cluster 4 Serveurs      8        Oui 346/s        80%  179% (+374% vs mono serveur TLS)
V3.6.1 Cluster 1 Serveurs      4        Non 75/s         75%  -39% (Référence mono serveur Sans TLS)
V3.6.1 Cluster 2 Serveurs      4        Non 116/s        90%  -8% (+55% vs mono serveur Sans TLS)
V3.6.1 Cluster 1 Serveurs      8        Non 86/s         25%  18% (+15% vs mono serveur Sans TLS)
V3.6.1 Cluster 2 Serveurs      8        Non 179/s        45%  45% (+139% vs mono serveur Sans TLS)
V3.6.1 Cluster 3 Serveurs      8        Non 266/s        60%  115% (+255% vs mono serveur Sans TLS)
V3.6.1 Cluster 4 Serveurs      8        Non 344/s        80%  178% (+359% vs mono serveur Sans TLS)
============================== ======== === ============ ==== =======================================

**Analyse**

Il ressort de ces benchmarks qu'il est important d'avoir au moins 2 core (4 threads)
dédiés par serveur Waarp R66 pour être optimal. En termes de mémoire,
entre 4 et 8 GB étaient alloués à chaque instance, 8 GB étant confortable.

La performance attendue est fonction du processeur et de la mémoire, ainsi que de la vitesse du
disque sous-jacent, sans oublier le réseau, mais il est possible de viser en mono serveur
environ 150 transferts/s en mono serveur.

En mode cluster (de 2 à 4 serveurs), les performances sont, sur le même matériel
(4 core 32 GB répartis sur 1 à 4 instances Waarp R66 et 1 base PostgreSQL),
légèrement supérieures avec 2 instances en cluster (150/s contre 178/s) et ne font qu'augmenter avec
plus de 340 transferts/s avec 4 instances, soit une accélération quasi linéaire avec une charge
CPU globale de moitié (35% contre 80% en cluster).

Le surcoût de la synchronisation via la base est donc perceptible mais vite récupéré
en ajoutant au moins 2 instances (haute disponibilité) et peut largement dépasser
la capacité d'une mono-instance.


*Benchmarks Waarp Gateway FTP et Waarp FTP Server*

Il s'agit de benchmarks orientés FTP (Serveur ou Gateway).

**Benchmarks avec transferts séquentiels**

===================== ============== ============ ============ ========================
Modèle                Active         Passive      Accélération Description
===================== ============== ============ ============ ========================
FTP Natif 2 core      102/s          68/s         Référence    Petits transferts séquentiels avec reconnexion
FTP Natif 4 core      118/s          77/s         16%          Petits transferts séquentiels avec reconnexion
GW FTP 2 core         101/s          67/s         -1%          Petits transferts séquentiels avec reconnexion
GW FTP 4 core         113/s          77/s         11%          Petits transferts séquentiels avec reconnexion
GW FTP 4 core Postgre 113/s          77/s         11%          Petits transferts séquentiels avec reconnexion
===================== ============== ============ ============ ========================

**Benchmarks avec concurrence des clients et des transferts**

===================== =========================== ============ ========================
Modèle                 Mixte Active / Passive     Accélération Description
===================== =========================== ============ ========================
FTP 10 clients 2c     488/s                       Référence    10 clients avec transferts concurrents
FTP 50 clients 2c     1102/s                      Référence    50 clients avec transferts concurrents
FTP 100 clients 2c    1217/s                      Référence    100 clients avec transferts concurrents
FTP 10 clients 4C     1056/s                      116%         10 clients avec transferts concurrents
FTP 50 clients 4C     3233/s                      193%         50 clients avec transferts concurrents
FTP 100 clients 4c    4200/s                      245%         100 clients avec transferts concurrents
GW FTP 10 clients 2C  290/s                       Référence    10 clients avec transferts concurrents
GW FTP 50 clients 2C  260/s                       Référence    50 clients avec transferts concurrents
GW FTP 100 clients 2c 244/s                       Référence    100 clients avec transferts concurrents
GW FTP 10 clients 2C  224/s                       -4%          10 clients avec transferts concurrents avec PostGreSQL
GW FTP 50 clients 2C  260/s                       0%           50 clients avec transferts concurrents avec PostGreSQL
GW FTP 100 clients 2c 244/s                       0%           100 clients avec transferts concurrents avec PostGreSQL
GW FTP 10 clients 4C  383/s                       64%          10 clients avec transferts concurrents
GW FTP 50 clients 4C  1234/s                      375%         50 clients avec transferts concurrents
GW FTP 100 clients 4c 1350/s                      453%         100 clients avec transferts concurrents
===================== =========================== ============ ========================

**Analyse**

Il ressort de ces benchmarks qu'il est important d'avoir au moins 2 core (4 threads)
dédiés par serveur Waarp Gateway FTP pour être optimal. En terme de mémoire,
4 GB étaient alloués à chaque instance.

A noter que le client Waarp (basé sur FTP4J) est plus performant que l'implémentation Apache.

