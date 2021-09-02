    DROP TABLE IF EXISTS MARS_GLOBAL_COMMAND;  
    CREATE TABLE MARS_GLOBAL_COMMAND (  
    ID LONG AUTO_INCREMENT  PRIMARY KEY,  
    PLATEAU_SIZE_X INT(3) NOT NULL , 
    PLATEAU_SIZE_Y INT(3) NOT NULL,
	LOST_ROBOTS INT(4) NOT NULL,
	TOTAL_ROBOTS INT(4) NOT NULL,
	EXPLORED_SECTORS INT(4) NOT NULL	
    );  
