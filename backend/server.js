const express = require('express');
const mysql = require('mysql');
const cors = require('cors');
const multer = require('multer');
const path = require('path');
const bcrypt = require('bcrypt');
const { exec } = require('child_process');


const app = express();
app.use(cors()); 

app.use(express.json({limit: '50mb'}));

// Enable CORS (Cross-Origin Resource Sharing) for all routes
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.header('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.header('Access-Control-Allow-Credentials', true);
    next();
  });

const db = mysql.createConnection({
    host: 'localhost',
    user: 'njeb1',
    password: '0pama0tw',
    database: 'users'
});


app.get('/api/design', (req, res) => {
    db.query("CREATE TABLE IF NOT EXISTS aviation (id INT(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY, reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, username VARCHAR(100) NOT NULL, password VARCHAR(255), av_username VARCHAR(100) , av_password VARCHAR(255), bet_amount INT(6), x_amount INT(6), oddsList Json, Balance FLOAT, betTime VARCHAR(35), status Text)", (err, result) => {
            if (err) {
                console.log(err);
            } else {
                console.log(result);
            }
        });
});

// Command to execute
const command = 'cd /home/njabulo/applicationJav/application && gradle run';

// Function to run the command and keep it running
function runCommand() {
    exec(command, (err, stdout, stderr) => {
        if (err) {
            console.error('Error executing command:', err);
            return;
        }
        console.log('Command output:', stdout);
        console.log('command stderr:', stderr)
    });
}

// kill firefox, java and gradle
// const killCommand = 'pkill firefox && pkill java  pkill geckodriver ';
const killCommand = ['pkill firefox', 'pkill java', 'pkill geckodriver'];


// Route to trigger the command execution
app.get('/api/terminal', (req, res) => {
    runCommand();
    res.send('Command started');
});

// Route to trigger the command execution
app.get('/api/exit', (req, res) => {
    killCommand.forEach((command) => {
        exec(command, (err, stdout, stderr) => {
            if (err) {
                console.error('Error executing command:', err);
                return;
            }
            console.log('Command output:', stdout);
        });
    });
    res.send('Command started');
});

// cancel firefox using command
app.get('/api/kill', (req, res) => {
    exec('pkill firefox', (err, stdout, stderr) => {
        if (err) {
            console.error('Error executing command:', err);
            return;
        }
        console.log('Command output:', stdout);
    });
    res.send('Command started');
});


// get
app.get('/api/get', (req, res) => {
    db.query("SELECT * FROM aviation", (err, result) => {
        if (err) {
            console.log(err);
        } else {
            res.send(result);
        }
    });
}
);

app.get('/api/get1', (req, res) => {
    let a= 0;
    for (let i=0; i<5; i++);
    console.log(a);
}
);

app.get('/api/get', (req, res) => {
    db.query("SELECT * FROM aviation", (err, result) => {
        if (err) {
            console.log(err);
        } else {
            res.send(result);
        }
    });
}
);

app.post('/api/account', (req, res) => {
    const {id} = req.body;
    const sql = 'SELECT av_username, av_password, bet_amount, x_amount FROM aviation WHERE id = ?';
    db.query(sql, [id], async (err, result) => {
        if (err) {
            console.log(err);
        } else {
            res.send(result);
        }
    });
}
);


// add odds
app.post('/api/oddslist', async (req, res) => {
    try {
        let data = req.body;
        const sql = `Update aviation SET oddsList = ? WHERE id = '749'`;
        data = JSON.stringify(data);
        db.query(sql, [data], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// get odds
app.get('/api/getodds', async (req, res) => {
    try {
        const sql = `SELECT oddsList FROM aviation WHERE id = '749'`;
        db.query(sql, (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});


// get balance
app.post('/api/balance', async (req, res) => {
    try {
        const { id } = req.body;
        const sql = `SELECT balance FROM aviation WHERE id = ?`;
        db.query(sql, [id], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// get bet Time
app.post('/api/betTime', async (req, res) => {
    try {
        const { id } = req.body;
        const sql = `SELECT betTime FROM aviation WHERE id = ?`;
        db.query(sql, [id], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// get status
app.post('/api/status', async (req, res) => {
    try {
        const { id } = req.body;
        const sql = `SELECT status FROM aviation WHERE id = ?`;
        db.query(sql, [id], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// update balance
app.post('/api/updateBalance', async (req, res) => {
    try {
        const { balance } = req.body;
        const sql = `Update aviation SET balance = ? WHERE id = '749'`;
        db.query(sql, [balance], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// update balance
app.post('/api/updateStatus', async (req, res) => {
    try {
        const { status } = req.body;
        const sql = `Update aviation SET status = ? WHERE id = '749'`;
        db.query(sql, [status], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});

// update Bet Time
app.post('/api/updateBetTime', async (req, res) => {
    try {
        const { betTime} = req.body;
        const sql = `Update aviation SET betTime = ? WHERE id = '749'`;
        db.query(sql, [betTime], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            } else {
                console.log(betTime);
                return res.json({ success: true, userResult });
            }
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});


// add user using post request
app.post('/api/add', async (req, res) => {
    try {
        const { username, password } = req.body;

        const salt = await bcrypt.genSalt(10);
        const hashedPass = await bcrypt.hash(password, salt);

        // Insert user data into 'users' table
        const userSql = "INSERT INTO aviation (username, password, id) VALUES (?, ?, '749')";
        db.query(userSql, [username, hashedPass], (userErr, userResult) => {
            if (userErr) {
                console.log(userErr);
                return res.status(500).json({ error: 'Internal Server Error' });
            }

        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Internal Server Error' });
    }
});
 
// register
// login
app.post('/api/login', async (req, res) => {
    const { username, password} = req.body;

    const sql = 'SELECT id, password FROM aviation WHERE username = ?';

    db.query(sql, [username], async (err, result)=> {
        if (err) {
            console.log(err)
            return res.status(500).json({error: 'Internal Server Error' })
        } else {
            if (result.length >0){
                const passwordMatch = await bcrypt.compare(password, result[0].password);

                if (passwordMatch) {
                    const userId = result[0].id
                    return res.json({ userId });
                } else {
                    return res.json({messag: "Invalid password"})
                }
            } else {
                return res.json({messag: "The combination of username and password do not match"})
            }
        }
    });
});

// update user subjects based on given data
app.post("/api/keys", (req, res) => {
    const data = req.body;
    // console.log(values);
    var instructions = "";
    const keys = Object.keys(data);
    const valus = Object.values(data);

    var value = []

    for (let i =0; i<Object.keys(data).length; i++){
        if (valus[i] !== "" && keys[i] !== "id" ) {
            instructions += `${keys[i]} = ?, `;
            value.push(valus[i]);
        } 
    }

    instructions = instructions.substring(0, instructions.length-2);
    value.push(data["id"]);

    console.log(data);

    const sql = `Update aviation SET ${instructions} WHERE id = ?`;
    db.query(sql, value, (err, result) => {
        if (err) {
            console.log(err);
            return res.status(500).json({ error: 'Internal Server Error' });
        } else {
            return res.json({ success: true, result });
            
        }
    });

});

app.get('/api/', (req, res) => {
    return res.json("from backend");
});

app.listen(8081, () => {
    console.log("listening");
});
