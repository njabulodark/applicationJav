echo "Switching to brand master"
git checkout master

echo "Building app..."
npm run build

echo "Deploying files to server...."
scp -r build/* njabulo@172.174.153.102:/var/www/172.174.153.102/

echo "Done!"

# C:/Users/njabulo/Documents/code/aviation2/applicationJav