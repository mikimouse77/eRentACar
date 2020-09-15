using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eRentACar.Models
{
    [Table("AuthenticationToken")]
    public class AuthenticationToken
    {
        [Key]
        public int AuthenticationTokenId { get; set; }
        public string Value { get; set; }

        public DateTime? Time { get; set; }
        public string IpAddress { get; set; }
        public string DeviceInfo { get; set; }

        public int? UserId { get; set; }
        [ForeignKey("UserId")]
        public virtual User User { get; set; }
    }
}