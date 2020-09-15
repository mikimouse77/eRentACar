namespace eRentACar.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.AuthenticationToken",
                c => new
                    {
                        AuthenticationTokenId = c.Int(nullable: false, identity: true),
                        Value = c.String(),
                        Time = c.DateTime(),
                        IpAddress = c.String(),
                        DeviceInfo = c.String(),
                        UserId = c.Int(),
                    })
                .PrimaryKey(t => t.AuthenticationTokenId)
                .ForeignKey("dbo.Users", t => t.UserId)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        UserId = c.Int(nullable: false, identity: true),
                        FirstName = c.String(),
                        LastName = c.String(),
                        Phone = c.String(),
                        Email = c.String(),
                        UserName = c.String(),
                        PasswordSalt = c.String(),
                        PasswordHash = c.String(),
                    })
                .PrimaryKey(t => t.UserId);
            
            CreateTable(
                "dbo.Cars",
                c => new
                    {
                        CarId = c.Int(nullable: false, identity: true),
                        CarName = c.String(),
                        Price = c.Decimal(nullable: false, precision: 18, scale: 2),
                    })
                .PrimaryKey(t => t.CarId);
            
            CreateTable(
                "dbo.Rentals",
                c => new
                    {
                        RentalId = c.Int(nullable: false, identity: true),
                        FromDate = c.DateTime(nullable: false),
                        ToDate = c.DateTime(nullable: false),
                        CarId = c.Int(nullable: false),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.RentalId)
                .ForeignKey("dbo.Cars", t => t.CarId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.CarId)
                .Index(t => t.UserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Rentals", "UserId", "dbo.Users");
            DropForeignKey("dbo.Rentals", "CarId", "dbo.Cars");
            DropForeignKey("dbo.AuthenticationToken", "UserId", "dbo.Users");
            DropIndex("dbo.Rentals", new[] { "UserId" });
            DropIndex("dbo.Rentals", new[] { "CarId" });
            DropIndex("dbo.AuthenticationToken", new[] { "UserId" });
            DropTable("dbo.Rentals");
            DropTable("dbo.Cars");
            DropTable("dbo.Users");
            DropTable("dbo.AuthenticationToken");
        }
    }
}
