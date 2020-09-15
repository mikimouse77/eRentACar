using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace eRentACar.Models
{
    [Table("AutorizacijskiToken")]
    public class AuthenticationToken
    {
        [Key]
        public int AutorizacijskiTokenID { get; set; }
        public string Vrijednost { get; set; }

        public Nullable<System.DateTime> VrijemeEvidentiranja { get; set; }
        public string IpAdresa { get; set; }
        public string DeviceInfo { get; set; }

        public Nullable<int> TuristId { get; set; }
        [ForeignKey("TuristId")]
        public virtual User Turist { get; set; }
    }
}