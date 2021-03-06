USE c_cs108_adchang1;

drop table if exists Question;
create table Question(
  qID int primary key,
  qType int,
  qText char(255),
  numAnswers int,
  isOrdered bool
);

drop table if exists PR;
create table PR(
  qID int,
  qPicUrl char(255)
);

drop table if exists Answer;
create table Answer(
  qID int,
  answerKey char(255),
  answerText char(255),
  answerOrder int,
  score decimal(6,4)
);

drop table if exists Quiz;
create table Quiz(
  quizID int primary key,
  authorID char(255),
  isRandomized bool,
  prevID int,
  isFlashcard bool,
  allowsPractice bool,
  immediateFeedback bool,
  description char(255),
  category char(255),
  quizName char(255),
  timeCreated timestamp default current_timestamp
);

drop table if exists Tag;
create table Tag(
  quizID int,
  tag char(255)
);

drop table if exists QuizQuestion;
create table QuizQuestion(
  quizID int,
  qID int
);

drop table if exists QuizUser;
create table QuizUser(
  username char(255) primary key,
  password char(255),
  salt char(10),
  isAdmin bool,
  isPerfPrivate bool,
  isPagePrivate bool,
  isDeact bool,
  usedPracticeMode bool,
  hadHighScore bool
);

drop table if exists Friend;
create table Friend(
  userID1 char(255),
  userID2 char(255)
);

drop table if exists Message;
create table Message(
  fromID char(255),
  toID char(255),
  subject char(255),
  messageText varchar(1000),
  status int,
  messageTime timestamp default current_timestamp,
  msgType char(255)
);

drop table if exists Challenge;
create table Challenge(
  quizID int,
  score double,
  fromID char(255),
  toID char(255),
  challengeTime timestamp default current_timestamp,
  status int
);

drop table if exists Request;
create table Request(
  fromID char(255),
  toID char(255),
  status int,
  requestTime timestamp default current_timestamp
);

drop table if exists Announcement;
create table Announcement(
  adminID char(255),
  announcementText varchar(1000),
  subject char(255),
  announcementID int,
  announcementTime timestamp default current_timestamp
);

drop table if exists Comment;
create table Comment(
	announcementID int,
	commentText varchar(1000),
	userID char(255),
	commentTime timestamp default current_timestamp
);

drop table if exists Rating;
create table Rating(
  userID char(255),
  quizID int,
  rating int,
  ratingTime timestamp
);

drop table if exists Attempts;
create table Attempts(
  userID char(255), 
  quizID int, 
  score double, 
  timeSpent int,
  timeTaken timestamp default current_timestamp
  
);

drop table if exists Achievements;
create table Achievements(
  userID char(255), 
  achievementID int, 
  timeAchieved timestamp
);

drop table if exists Review;
create table Review(
  userID char(255),
  quizID int,
  review varchar(1000),
  reviewTime timestamp
);

drop table if exists Reported;
create table Reported(
  quizID int,
  occurrence int,
  date timestamp default current_timestamp
);

drop table if exists Forum;
create table Forum(
  userID char(255),
  quizID int,
  postText varchar(1000),
  date timestamp default current_timestamp
);


INSERT into QuizUser VALUES("admin","2ea66167ce4e9bd144ae6dd8f6082e2846518e33","0pq5lf.qjn",1,0,0,0,0,0);
INSERT into QuizUser 
VALUES("test1","2922d3a91a163473252e64b0f5e99bdb929b02b7","9r918n.4zx",0,0,0,0,0,0);
INSERT into QuizUser 
VALUES("test2","d08027699144bad9ec55b8d203a90c816491148c","9rg!-sjmxb",0,0,0,0,0,0);

INSERT into Friend
VALUES("admin","test1");
INSERT into Friend
VALUES("test1","admin");
INSERT into Friend
VALUES("test2","test1");
INSERT into Friend
VALUES("test1","test2");

INSERT into Announcement
VALUES ("admin","checking the announcement table is changed","website creation", 1, null);
