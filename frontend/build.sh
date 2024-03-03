echo "Switching to brand master"
git checkout master

echo "Building app..."
npm run build

echo "Deploying files to server...."
scp -r build/* njabulo@102.37.33.157:/var/www/102.37.33.157/

echo "Done!"

# C:/Users/njabulo/Documents/code/aviation2/applicationJav