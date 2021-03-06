package io.infrastructor.core.inventory

import org.junit.Test

import static io.infrastructor.core.inventory.InlineInventory.inlineInventory

class InlineInventoryTest {

    @Test
    void mapLikeDeclaration() {
        def inventory = inlineInventory {
            node id: "idX", host: "10.0.0.1", port: 10000, username: "root", password: "infra", tags: [x: "t1", y: "t2"]
            node id: "idY", host: "10.0.0.2", port: 10000, username: "root", password: "infra"
        }
        
        assert inventory.size() == 2
        
        def idX = inventory.find {it.id == 'idX'}
        assert idX
        idX.with {
            assert port == 10000
            assert host == "10.0.0.1"
            assert username == "root"
            assert password == "infra"
            assert tags == [x: "t1", y: "t2"]
        }
        
        def idY = inventory.find {it.id == 'idY'}
        assert idY
        idY.with {
            assert port == 10000
            assert host == "10.0.0.2"
            assert username == "root"
            assert tags == [:]
        }
    }
    
    @Test
    void closureLikeDeclaration() {
        def inventory = inlineInventory {
            node {
                id = "idX"
                host = "10.0.0.1"
                port = 10000
                username = "root"
                password = "infra"
                tags = [x: "t1", y: "t2"]
            }
            
            node {
                id = "idY"
                host = "10.0.0.2"
                port = 10000
                username = "root"
                password = "infra"
            }
        }
        
        assert inventory.size() == 2
        
        def idX = inventory.find {it.id == 'idX'}
        assert idX
        idX.with {
            assert port == 10000
            assert host == "10.0.0.1"
            assert username == "root"
            assert password == "infra"
            assert tags == [x: "t1", y: "t2"]
        }
        
        def idY = inventory.find {it.id == 'idY'}
        assert idY
        idY.with {
            assert port == 10000
            assert host == "10.0.0.2"
            assert username == "root"
            assert tags == [:]
        }
    }
    
    @Test
    void combinedDeclaration() {
        def inventory = inlineInventory {
            node(id: "idX", host: "10.0.0.1") {
                port = 10000
                username = "root"
                password = "infra"
                tags = [x: "t1", y: "t2"]
            }
            
            node(port: 10000, password: "infra", tags: [x: "t3"]) {
                id = "idY"
                host = "10.0.0.2"
                username = "root"
            }
        }
        
        assert inventory.size() == 2
        
        def idX = inventory.find {it.id == 'idX'}
        assert idX
        idX.with {
            assert port == 10000
            assert host == "10.0.0.1"
            assert username == "root"
            assert password == "infra"
            assert tags == [x: "t1", y: "t2"]
        }
        
        def idY = inventory.find {it.id == 'idY'}
        assert idY
        idY.with {
            assert port == 10000
            assert host == "10.0.0.2"
            assert username == "root"
            assert tags == [x: "t3"]
        }
    }
}

