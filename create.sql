USE c_cs108_sarya;

drop table if exists Question;
create table Question(
  qID int primary key,
  qType int
);

drop table if exists QR;
create table QR(
  qID int,
  qText char(255)
);

drop table if exists Answer;
create table Answer(
  qID int,
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
  category char(255)
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
  isPublic bool
);

drop table if exists Friend;
create table Friend(
  userID1 char(255),
  userID2 char(255)
);

drop table if exists FriendRequest;
create table FriendRequest(
  userID1 char(255),
  userID2 char(255)
);

drop table if exists Message;
create table Message(
  fromID char(255),
  toID char(255),
  subject char(255),
  messageText char(255),
  status int,
  messageTime timestamp
);

drop table if exists Challenge;
create table Challenge(
  quizID int,
  fromID char(255),
  toID char(255),
  challengeTime timestamp,
  status int
);

drop table if exists Request;
create table Request(
  fromID char(255),
  toID char(255),
  status int,
  requestTime timestamp
);

drop table if exists Announcement;
create table Announcement(
  adminID char(255),
  announcementText char(255),
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

insert into QuizUser values ('sarya', 'pass', 1);