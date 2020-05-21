# @author: Ubaada
# used for jUnit Tests of the webservice.

INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('bob64','A1','Bob','Comm','comm1@otago.ac.nz','BIO','Committee','bob123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('john98','A4','Johnny','Depp','dep1@otago.ac.nz','COSC','Department','dep123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('kyle92','A7','Kyle','Nom','nom1@otago.ac.nz','COSC','Nominee','nom123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('obama08','A8','Obama','Barack','boab@otago.ac.nz','MED','Nominee','bar123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('tim23','A9','Tim','Smith','smit@otago.ac.nz','BIO','Nominee','tim123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('kimi27','A10','Kim','George','geki@otago.ac.nz','MED','Nominee','kim123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('hen49','A11','henry','lee','hen@otago.ac.nz','COSC','Nominee','hen123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('jen03','A12','jeanie','smith','jensi@otago.ac.nz','BIO','Nominee','jen123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('pel05','A5','Pele','Sanchez','psa@otago.ac.nz','MED','Department','pel123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('jck84','A6','Jack','Feng','jacf@otago.ac.nz','BIO','Department','jck123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('stan22','A2','Stan','Marsh','stanm@otago.ac.nz','CHEM','Committee','stan123');
INSERT INTO "User" ("userID","staffID","firstName","lastName","email","department","userType","password") VALUES ('mic55','A3','Michael','Jordan','micj@otago.ac.nz','PHY','Committee','mic123');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app99','kyle92','Best-Researcher','cv.com','i''m the best','Yes he is','Pending');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app100','obama08','Best-Researcher','www.cv.com/hello.html','I''m hard working.','Professor Moriarity','Pending');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app101','tim23','Best-Teacher','www.cv.com/nocv.html','don''t vote for me.','Professor X','Pending');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app102','kimi27','Best-Teacher','www.cv.com/asd.html','only I deserve this.','Mom','Pending');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app103','jen03','Best-Researcher','www.cv.com/hwh.html',NULL,'Prof. Eric','Pending.');
INSERT INTO "Application" ("applicationID","userCandidate","category","linkToCV","personalStatement","candidateReferences","status") VALUES ('app104','hen49','Best-Teacher','www.cv.com/hen-cv.pdf',NULL,'Prof. Li
Prof. Rose','Pending');
