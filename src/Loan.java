import java.util.Date;

public class Loan {
    public int LoanId;
    public int MemberId;
    public int CopyId;
    public Date LoanDate;
    public Date DueDate;

    public Loan(int loanId, int memberId, int copyId, Date loanDate, Date dueDate) {
        this.LoanId = loanId;
        this.MemberId = memberId;
        this.CopyId = copyId;
        this.LoanDate = loanDate;
        this.DueDate = dueDate;
    }

    public int getLoanId() {
        return this.LoanId;
    }

    public void setLoanId(int loanId) {
        this.LoanId = loanId;
    }

    public Date getDueDate() {
        return this.DueDate;
    }

    public void setDueDate(Date dueDate) {
        this.DueDate = dueDate;
    }

    public Date getLoanDate() {
        return this.LoanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.LoanDate = loanDate;
    }

    public int getMemberId() {
        return this.MemberId;
    }

    public void setMemberId(int memberId) {
        this.MemberId = memberId;
    }

    public int getCopyId() {
        return this.CopyId;
    }

    public void setCopyId(int copyId) {
        this.CopyId = copyId;
    }
}
