USE c_cs108_adchang1;

drop table if exists Question;
create table Question(
  qID int primary key,
  qType int,
  numAnswers int,
  isOrdered bool
);

drop table if exists QR;
create table QR(
  qID int,
  qText char(255)
);

drop table if exists FIB;
create table FIB(
  qID int,
  qText char(255)
);

drop table if exists PR;
create table PR(
  qID int,
  qText char(255),
  qPicUrl char(255)
);

drop table if exists Answer;
create table Answer(
  qID int,
  answerKey char(255),
  answerText char(255),
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
  quizName char(255)
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
  announcementTime timestamp
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
  score int, 
  timeTaken timestamp
);

drop table if exists Achievements;
create table Achievements(
  userID char(255), 
  achievementID int, 
  timeAchieved timestamp
);

INSERT into QuizUser VALUES("admin","2ea66167ce4e9bd144ae6dd8f6082e2846518e33","0pq5lf.qjn",1,0,0,0,0,0);
