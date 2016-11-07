package citaapp.citapprenticeshipapp;

/**
 * Created by Donald on 7/11/2016.
 */

public class CITDepartment
{
    private int deptId;
    private String deptName;
    private String deptPhone;
    private String deptEmail;

    public CITDepartment()
    {

    }

    public CITDepartment(String name, String phone, String email)
    {
        this.deptName = name;
        this.deptPhone = phone;
        this.deptEmail = email;
    }

    public CITDepartment(int id, String name, String phone, String email)
    {
        this.deptId = id;
        this.deptName = name;
        this.deptPhone = phone;
        this.deptEmail = email;
    }

    public void setID(int id)
    {
        this.deptId = id;
    }

    public void setName(String name)
    {
        this.deptName = name;
    }

    public void setPhone(String phone)
    {
        this.deptPhone = phone;
    }

    public void setEmail(String email)
    {
        this.deptEmail = email;
    }

    public int getId()
    {
        return deptId;
    }

    public String getName()
    {
        return deptName;
    }

    public String getPhone()
    {
        return deptPhone;
    }

    public String getEmail()
    {
        return deptEmail;
    }
}
