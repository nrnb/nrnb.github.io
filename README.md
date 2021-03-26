**NRNB Web Site Source**

This is the code repository for http://www.nrnb.org.

*Developers: feel free to fork this code and make pull requests or request direct editing permissions. You can preview the latest code changes in this repo at http://nrnb.github.io/. After changes have been approved, we will pull from this repo to update the hosted code serving the live site.*

* Server: nrnb.ucsd.edu
* VirtualHost: nrnb.conf
* Dir: nrnb.org
* Logs: /var/log/apache2/nrnb_access.log and nrnb_error.log
* Server admin: apico@gladstone.ucsf.edu
* Domain name: NetworkSolutions.com (apico)

Note: The contents of the ```presentation``` directory are excluded from the repo due to their size, many are over 100MB each. Be sure to scp (the scp command) to securely copy files and directories between remote hosts without starting an FTP session or logging into the remote systems explicitly. The scp command uses SSH to transfer data, so it requires a password or passphrase for authentication. these files over from a pre-existing repo when setting up a new one.
