import java.util.Date;

public class Loan {
    int LoanId;
    int MemberId;
    int CopyId;
    Date LoanDate;
    Date DueDate;

    public Loan(int loanId, int memberId, int copyId, Date loanDate, Date dueDate) {
        LoanId = loanId;
        MemberId = memberId;
        CopyId = copyId;
        LoanDate = loanDate;
        DueDate = dueDate;
    }

    public int getLoanId() {
        return LoanId;
    }

    public void setLoanId(int loanId) {
        LoanId = loanId;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public Date getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(Date loanDate) {
        LoanDate = loanDate;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public int getCopyId() {
        return CopyId;
    }

    public void setCopyId(int copyId) {
        CopyId = copyId;
    }
}
