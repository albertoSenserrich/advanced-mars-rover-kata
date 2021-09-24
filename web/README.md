
# Web-module of mars-rovers-kata

This project has de business logic related to mars rover kata. 
You can execute it after generate the project and execute it from command line. 
This project can be imported to other projects in order to use this business logic.

## 1.0 How to build and execute it
	1 - Generate the code using the maven comand: "mvn clean package"
	2 - Ejecute application using spring boot with maven command comand "mvn spring-boot:run spacestation-x.y.z.jar this will start the application
   
   
# WS Operations

**Movement request**
----
  Creates a request for mars rovert movements and returns final positions of rovers

* **URL**

  /marsRovertRequest/

* **Method:**

  `POST`


* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ id : 12, name : "Michael Bloom" }`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "User doesn't exist" }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

**Sample Call:**
  ```
  "plateau": {
		"x": 5,
		"y": 5
	},
	"robots": [
		{
			"position": {
				"x": 1,
				"y": 2,
				"orientation": "N"
			},
			"orders": "LFLFF"
		},
		{
			"position": {
				"x": 3,
				"y": 3,
				"orientation": "E"
			},
			"orders": "FRFRRF"
		}
	]
}
  ```
 **Sample Response:**
    ```
  {
    "finalCoordinates": [
        {
            "x": 0,
            "y": 0,
            "orientation": "S"
        },
        {
            "x": 4,
            "y": 3,
            "orientation": "N"
        }
    ],
    "detailMessage": null,
    "id": 1
}
  ```

**Health check**
----
Health check in order to confirm if application is on-line

* **URL**

  /adminService/healthCheck

* **Method:**
  `GET`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `Space station on-line`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />



