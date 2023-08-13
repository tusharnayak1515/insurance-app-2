import User from 'src/app/models/User'; 

class Question {
  constructor(
    public questionId: number | null,
    public user: User,
    public questionText: string,
    public answerText: string,
    public createdAt: Date,
    public updatedAt: Date
  ) {}
}

export default Question;
