#!/bin/bash

# Check if the script is run as root
if [ "$EUID" -ne 0 ]; then
  echo "Please run this script as root."
  exit 1
fi

# Update the package list
apt update

# Install Nginx
apt install -y nginx

# Enable and start Nginx service
systemctl enable nginx
systemctl start nginx

# Create a basic Nginx configuration file for your website
bash -c 'cat > /etc/nginx/sites-available/172.174.153.102 <<EOF
server {
    listen 80;
    listen [::]:80;

    root /var/www/172.174.153.102;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }

}
EOF'

# Create the document root for your website
mkdir -p /var/www/172.174.153.102

# giving permision
chmod 755 -R /var/www/172.174.153.102

# njabulo is the username
chown -R njabulo:www-data /var/www/172.174.153.102

# Create a sample index.html file
echo "<html><body><h1>Welcome to 172.174.153.102</h1></body></html>" > /var/www/172.174.153.102/index.html

# unink default
unlink /etc/nginx/sites-enabled/default
# Create a symbolic link to enable the site
ln -s /etc/nginx/sites-available/172.174.153.102 /etc/nginx/sites-enabled/

# Test Nginx configuration
nginx -t

# Reload Nginx to apply the changes
systemctl reload nginx

echo "Nginx has been installed and configured for your website. Visit http://172.174.153.102 to see your website."